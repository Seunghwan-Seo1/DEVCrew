//서승환

package com.mysite.portfolio.admin;

import java.io.IOException;
import java.util.List;

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
import com.mysite.portfolio.visitor.VisitorService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
	
	private final MemberService memberService;
	
	private final FestivalService festivalService;
	
    private final VisitorService visitorService;

	


	@GetMapping("/main")
	public String adminmain(Model model) {
        Integer visitorCount = visitorService.getTotalVisitorCount(); // 총 방문자 수 가져오기
        model.addAttribute("visitorCount", visitorCount); // 모델에 추가
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
	

	/*
	 * @PostMapping("/festivalupdate") public String festivalupdate(@ModelAttribute
	 * Festival festival , @RequestParam("file") MultipartFile File ) throws
	 * IOException { festivalService.update(festival, File);
	 * 
	 * return "redirect:/admin/festivalconfig"; }
	 */
	 @PostMapping("/festivalupdate")
	    public String festivalupdate(@ModelAttribute Festival festival, @RequestParam("files") List<MultipartFile> files) throws IOException {
	        festivalService.update(festival, files);
	        return "redirect:/admin/festivalconfig";
	    }
	
	
	

	}


