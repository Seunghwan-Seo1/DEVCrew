//생산자 : 이진호
package com.mysite.portfolio.user;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserAuthenService {

	private UserRepository userRepository;
	
	public SiteUser authen() {
		//접속자 정보 추출
		Authentication authentication = 
				      SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();	
		String username = userDetails.getUsername();
		Optional<SiteUser> oc = userRepository.findByusername(username);
		
		return oc.get();
	}
	
}
