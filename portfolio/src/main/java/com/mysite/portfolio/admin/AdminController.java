//서승환

package com.mysite.portfolio.admin;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.festival.Festival;
import com.mysite.portfolio.festival.FestivalService;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
	
	private final MemberService memberService;
	
	private final FestivalService festivalService;
	


	@GetMapping("/main")
	public String adminmain() {
		return "admin/main";
	}
	
	
	
	
	  @GetMapping("/userconfig") public String member(Model model) {
	  model.addAttribute("members", memberService.readlist()); 
	  return "admin/userconfig"; }
	 

	@GetMapping("/festivalconfig")
	public String festival(Model model) {
		model.addAttribute("festivals", festivalService.readlist());
		return "admin/festivalconfig";
	}
	
	@PostMapping("/festivalupdate")
	public String festivalupdate(@ModelAttribute Festival festival , @RequestParam("file") MultipartFile File
			) throws IOException  {
			festivalService.update(festival, File);
			
			return "redirect:/admin/festivalconfig";
			}
	    
	}


