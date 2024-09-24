package com.mysite.portfolio.festival;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/freview")
@RequiredArgsConstructor
@Controller
public class FreviewController {

	private final FreviewService freviewService;
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FestivalService festivalService;
	
	
	//create
	@PostMapping("/create")
	public String create(@ModelAttribute Freview freview,
					     @RequestParam("fid") Integer fid,  Principal principal
			) throws IOException {
		freviewService.create(freview, fid,  memberService.getMember(principal.getName()));
		return "redirect:/festival/readdetail/" + fid;
	}
	
	
	
	 // readlist
    @GetMapping("/readlist") // 축제 리뷰 목록
    public String readlist(Model model) {
        model.addAttribute("freviews", freviewService.frvlist());
        return "festival/readlist";
    }
	
	
	
	
	
	// Update
	
	
	@PostMapping("/update/{frid}") // Create @ModelAttribute 주로사용
	public String update(@ModelAttribute Freview freview
					     )  {
		freviewService.update(freview);
		
		
    	return "redirect:/festival/readdetail";
	}
	

    // Delete
	    @PostMapping("/delete/{frid}")
	    public String delete(@PathVariable("frid") Integer frid,Principal principal) {
			Freview freview = this.freviewService.getFreview(frid);
	    	freviewService.delete(frid);
	    	return String.format("redirect:/festival/readdetail/%s", freview.getFestival().getFid());
	    }
    
 // 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{frid}")
    public String freviewVote(Principal principal, @PathVariable("frid") Integer frid) {
        Freview freview = this.freviewService.getFreview(frid); // Freview로 수정
        Class<? extends Object> member = this.memberService.getClass();
        this.freviewService.vote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", frid);
    }

    // 비추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/devote/{frid}")
    public String freviewDevote(Principal principal, @PathVariable("frid") Integer frid) {
        Freview freview = this.freviewService.getFreview(frid); // 메소드 이름 일관성 유지
        Class<? extends Object> member = this.memberService.getClass();
        this.freviewService.devote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", frid);
    }
}