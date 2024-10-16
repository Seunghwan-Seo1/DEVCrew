// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.mysite.portfolio.DataNotFoundException;
import com.mysite.portfolio.MailService;
import com.mysite.portfolio.admin.NotificationRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final HttpServletRequest req;
	private final NotificationRepository notificationRepository;
	
	//회원 가입
	public void create(MemberForm memberForm) {
	    
	    Member member = new Member();
	    member.setUsername(memberForm.getUsername());
	    member.setPassword(passwordEncoder.encode(memberForm.getPassword1()));
	    member.setRole("ROLE_USER");
	    member.setNickname(memberForm.getNickname());
	    member.setMaddr(memberForm.getMaddr());
	    member.setMdate(LocalDateTime.now());
	    
	    this.memberRepository.save(member);
	    
	    

	}
	
	//회원 가입 시 폼 유효성 검사
	public void validateMemberForm(MemberForm memberForm) {
		
	    // 아이디 중복 확인
	    Optional<Member> member = memberRepository.findByusername(memberForm.getUsername());
	    if (member.isPresent()) {
	        throw new IllegalArgumentException("이미 등록된 사용자입니다.");
	    }
	    
	    // 비밀번호 확인
	    if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
	        throw new IllegalArgumentException("2개의 패스워드가 일치하지 않습니다.");
	    }
	    
	    // 연락처 중복 방지
	    Optional<Member> existingMemberByNickname = memberRepository.findByNickname(memberForm.getNickname());
	    if (existingMemberByNickname.isPresent()) {
	        throw new IllegalArgumentException("이미 등록된 닉네임입니다.");
	    }
	    
	    // 연락처 중복 방지
	    Optional<Member> existingMemberByMaddr = memberRepository.findByMaddr(memberForm.getMaddr());
	    if (existingMemberByMaddr.isPresent()) {
	        throw new IllegalArgumentException("이미 등록된 연락처입니다.");
	    }

	}
	
	//회원 가입 에러 메세지
	public void handleSignupException(Exception e, BindingResult bindingResult) {
	    if (e instanceof IllegalArgumentException) {
	        String message = e.getMessage();
	        if (message.contains("이미 등록된 사용자")) {
	            bindingResult.rejectValue("username", "usernameExists", message);
	        } else if (message.contains("2개의 패스워드")) {
	            bindingResult.rejectValue("password2", "passwordMismatch", message);
	        } else if (message.contains("이미 등록된 닉네임")) {
	        	bindingResult.rejectValue("nickname", "nicknameExists", message);
	        } else if (message.contains("이미 등록된 연락처")) {
	            bindingResult.rejectValue("maddr", "maddrExists", message);
	        } else {
	            bindingResult.reject("signupFailed", message);
	        }
	    } else if (e instanceof DataIntegrityViolationException) {
	        bindingResult.reject("signupFailed", "데이터 무결성 위반: " + e.getMessage());
	    } else {
	        bindingResult.reject("signupFailed", "회원가입 실패: " + e.getMessage());
	    }
	}
	
	//시큐리티 로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> _member = this.memberRepository.findByusername(username);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = _member.get();
        System.out.println("Loaded member: " + member); // 로그인 데이터 확인용 코드
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ROLE_ADMIN".equals(member.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
        
	}
	
	//소셜 로그인 확인
	public int logincheck(String username) throws UsernameNotFoundException {
		Optional<Member> tmember = memberRepository.findByusername(username);
		
		if (tmember.isEmpty()) {
			return 1;//db에 없음, 회원 가입으로
		}
		
		Member member = tmember.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("ROLE_ADMIN".equals(member.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
		
		//스프링 시큐리티 규격에 맞게 로그인 처리
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword(), authorities));
		HttpSession session = req.getSession(true);
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,sc);
	
		return 0; //db에 있음, 세션 처리까지
	}
	
	
    //회원 정보 조회
	public Member readdetail() {
      
      //접속자 정보 추출
      Authentication authentication = 
                  SecurityContextHolder.getContext().getAuthentication();
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();   
      String username = userDetails.getUsername();
      Optional<Member> oc = memberRepository.findByusername(username);
      
      return oc.get();

    }
   
	//회원 정보 수정
	public void update(Member member) {
		Optional<Member> _member = memberRepository.findByusername(member.getUsername());
		
		Member memberData = _member.get();
		memberData.setNickname(member.getNickname());
		memberData.setMaddr(member.getMaddr());
		this.memberRepository.save(memberData);
	}
	
	//회원 탈퇴
	@Transactional
	public void delete(Integer id) {
		this.notificationRepository.deleteByRecipientMid(id);
		this.memberRepository.deleteById(id);
	}
	
	//아이디 찾기
	public Optional<String> idsearch(String maddr) {
        return memberRepository.findByMaddr(maddr)
        		.map(Member::getUsername);
    }

	public List<Member> readlist() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findByUsername(String username) {
	    return memberRepository.findByusername(username);
	}
	
	public List<Member> findByRole(String role) {
	    return memberRepository.findByRole(role);
	}
	
	public Member getMember (String username) {
        Optional<Member> member = this.memberRepository.findByusername(username);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }


	}
	
	public void updateUserRole(Integer memberId, String newRole) {
	    Optional<Member> optionalMember = memberRepository.findById(memberId);
	    if (optionalMember.isPresent()) {
	        Member member = optionalMember.get();
	        member.setRole(newRole); // 새로운 역할 설정
	        memberRepository.save(member); // 변경 사항 저장
	    } else {
	        throw new IllegalArgumentException("회원 ID를 찾을 수 없습니다.");
	    }
	}


}
