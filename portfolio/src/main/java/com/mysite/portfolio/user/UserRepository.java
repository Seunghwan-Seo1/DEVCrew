package com.mysite.portfolio.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//생산자 : 이진호

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

	//스프링 시큐리티 로그인 아이디로 객체 찾기
	Optional<SiteUser> findByusername(String username);
}
