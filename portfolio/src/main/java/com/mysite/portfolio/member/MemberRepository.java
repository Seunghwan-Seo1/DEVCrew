//생산자 : 이진호
package com.mysite.portfolio.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	//스프링 시큐리티 로그인 아이디로 객체 찾기
	Optional<Member> findByusername(String username);

	//연락처로 객체 찾기
	Optional<Member> findByMaddr(String maddr);
}

