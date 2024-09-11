package com.mysite.portfolio.lodge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/lodge")
@Controller
public class LodgeController {
	
	// 숙박 메인페이지
		@GetMapping("/main")
		public String lmain() {
			return "/lodge/main";
		}
	
	// 상세 필터 검색 페이지

		
	// 숙박 상세 페이지
	

}
