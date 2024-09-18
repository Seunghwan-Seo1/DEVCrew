// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.mysite.portfolio.festival.Festival;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	//회원 가입
	public void create(MemberForm memberForm) {
	    
	    Member member = new Member();
	    member.setUsername(memberForm.getUsername());
	    member.setPassword(passwordEncoder.encode(memberForm.getPassword1()));
	    member.setRole("ROLE_USER");
	    member.setMdate(LocalDateTime.now());
	    member.setMaddr(memberForm.getMaddr());
	    
	    this.memberRepository.save(member);
	}
	
	//아이디 중복 방지 및 비밀번호 확인
	public void validateMemberForm(MemberForm memberForm) {
	    // 아이디 중복 방지
	    Optional<Member> existingMember = memberRepository.findByusername(memberForm.getUsername());
	    if (existingMember.isPresent()) {
	        throw new IllegalArgumentException("이미 등록된 사용자입니다.");
	    }
	    
	    // 비밀번호 확인
	    if (!memberForm.getPassword1().equals(memberForm.getPassword2())) {
	        throw new IllegalArgumentException("2개의 패스워드가 일치하지 않습니다.");
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
		this.memberRepository.save(member);
	}
	
	//회원 탈퇴
	public void delete(Integer id) {
		this.memberRepository.deleteById(id);
	}
	
	 // readlist
    public List<Member> readlist() {
        return memberRepository.findAll();
    }

}
