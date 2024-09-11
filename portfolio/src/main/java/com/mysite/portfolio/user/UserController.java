// 생산자 : 이진호
package com.mysite.portfolio.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	//회원 가입
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	@PostMapping("/signup")
	public String signup(SiteUser user) {
		userService.create(user);
		return "redirect:/signin";
	}
	
	//로그인
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
	
	//회원 정보 조회
	@GetMapping("/detail/{id}")
	public String readdetail() {
		
		return "";
	}
	
	//회원 정보 수정
	@GetMapping("/update/{id}")
	public String update() {
		
		return "";
	}
	
	//회원 탈퇴
	@GetMapping("/delete/{id}")
	public String delete() {
		
		return "";
	}
	
}
