package com.mysite.portfolio.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
	
	@GetMapping("/main")
	public String adminmain() {
		return "admin/main";
	}
	
	@GetMapping("/userconfig")
	public String userconfig() {
		return "admin/userconfig";
	}
	
}
