// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	//회원 가입
	public void create(Member member) {
		
		member.setMdate(LocalDateTime.now());
		member.setRole("ROLE_USER");
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		this.memberRepository.save(member);
		
	}
	
	//시큐리티 로그인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Member> _member = this.memberRepository.findByusername(username);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = _member.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ROLE_ADMIN".equals(member.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
        
	}
	
	//회원 정보 조회
	public Member readdetail(Integer mid) {
		Optional<Member> ob = memberRepository.findById(mid);
		return ob.get();
	}
	
	//회원 정보 수정
	public void update(Member member) {
		this.memberRepository.save(member);
	}
	
	//회원 탈퇴
	public void delete(Integer id) {
		this.memberRepository.deleteById(id);
	}

}
