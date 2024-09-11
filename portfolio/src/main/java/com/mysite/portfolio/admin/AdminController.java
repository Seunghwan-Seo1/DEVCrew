package com.mysite.portfolio.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
	
	private final MemberService memberService;
	
	@GetMapping("/main")
	public String adminmain() {
		return "admin/main";
	}
	
	
	
	@GetMapping("/userconfig")
	public String member(Model model) {
		model.addAttribute("members", memberService.readlist());
		return "admin/userconfig";
	}
}
