//생산자 : 이진호
package com.mysite.portfolio.member;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class MemberAuthenService {

	private MemberRepository memberRepository;
	
	public Member authen() {
		//접속자 정보 추출
		Authentication authentication = 
				      SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();	
		String username = userDetails.getUsername();
		Optional<Member> oc = memberRepository.findByusername(username);
		
		return oc.get();
	}
	
}
