package com.mysite.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final MemberService memberService;
	
	@GetMapping("/")
	public String index(){/*@RequestParam("mid") Integer mid) {
		
		memberService.readdetail(mid);*/
		return "index";
	}
	
	@GetMapping("/temp")
	public String temp() {
		return "temp";
	}
}

