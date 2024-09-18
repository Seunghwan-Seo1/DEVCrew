package com.mysite.portfolio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {
	
	private final MemberService memberService;
	
	@GetMapping("/")
	public String index(Model model){
		//  model.addAttribute("members", memberService.readdetail()); 

		return "index";
	}
	
	@GetMapping("/temp")
	public String temp() {
		return "temp";
	}
}

