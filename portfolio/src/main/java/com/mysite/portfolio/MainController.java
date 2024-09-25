package com.mysite.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@Autowired
	private MailService mailService;
	
	
	@GetMapping("/")
	public String index(){
		return "index";
	}
	
	@GetMapping("/temp")
	public String temp() {
		return "temp";
	}
	
	@GetMapping("/csr")
	public String csr() {
		return "csr";
	}
	
	@PostMapping("/csr")
	public String mailcreate(@RequestParam("title") String title, 
			@RequestParam("content") String content, 
			@RequestParam("senderName") String senderName ) {

			mailService.create(title, content, senderName);
			return "redirect:/csr";

}

}

