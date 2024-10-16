//생산자 : 이진호
package com.mysite.portfolio.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	//스프링 시큐리티 로그인 아이디로 객체 찾기
	Optional<Member> findByusername(String username);

	//연락처로 객체 찾기
	Optional<Member> findByMaddr(String maddr);
	
	//닉네임으로 객체 찾기
	Optional<Member> findByNickname(String nickname);
	
	List<Member> findByRole(String role);
	
}

