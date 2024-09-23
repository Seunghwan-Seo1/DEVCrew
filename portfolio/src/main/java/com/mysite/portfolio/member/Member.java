// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mid;
	
	//시큐리티 규격에 맞게 항상 4개는 항상 똑같이 넣어준다. 
	private String username;  // 회원 아이디, 이메일 주소로 하면 많이 편하다. 
	private String password;
	private boolean enabled;
	private String role;
	
	private String maddr; // 연락처(전화번호)
	
	private LocalDateTime mdate;
	
	
}
