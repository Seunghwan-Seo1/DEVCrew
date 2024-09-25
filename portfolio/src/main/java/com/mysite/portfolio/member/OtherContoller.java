package com.mysite.portfolio.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OtherContoller {

	@Autowired
	private MemberService memberService;
	
	//로그인
	@GetMapping("/signin")
	public String signin() {
		return "member/signin";
	}
	
	//네이버 로그인 콜백
	@GetMapping("/naverlogin")
	public String naverlogin() {
		return "member/callback";
	}
	
	//소셜 로그인
	@GetMapping("/logincheck")
	public String logincheck(@RequestParam("email") String email) {
		
		if (1 == memberService.logincheck(email)) {
			return "redirect:/member/signup?email=" + email; // 회원 가입 처리
		}else {
			return "redirect:/"; // 로그인 처리
		}
	}
		
}
