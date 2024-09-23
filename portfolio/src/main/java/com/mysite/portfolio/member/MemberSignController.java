// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class MemberSignController {

	@Autowired
	private MemberService memberService;
	
	//회원 가입
	@GetMapping("/signup")
	public String signup(MemberForm memberForm) {
		return "signup";
	}
	@PostMapping("/signup")
	public String signup(	@Valid MemberForm memberForm,
	                     	BindingResult bindingResult
	                     	) {
	    
	    if (bindingResult.hasErrors()) {
	        return "signup";
	    }

	    try { 
	        // 아이디 중복 방지 및 비밀번호 확인
	        memberService.validateMemberForm(memberForm);
	        
	        // 회원 가입
	        memberService.create(memberForm); 
	    } catch (Exception e) {
	        // 회원 가입 에러 메세지
	        memberService.handleSignupException(e, bindingResult);
	        
	        return "signup";
	    }

	    return "redirect:/signin";
	}

	//로그인
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

}
