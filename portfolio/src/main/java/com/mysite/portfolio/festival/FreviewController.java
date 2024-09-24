package com.mysite.portfolio.festival;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/freview")
@RequiredArgsConstructor
@Controller
public class FreviewController {

	private final FreviewService freviewService;
	@Autowired
	private MemberService memberService;
	
	
	//create
	
	@PostMapping("/create")
	public String create(@ModelAttribute Freview freview,
					     @RequestParam("frid") Integer frid
			) throws IOException {
		freviewService.create(freview, frid);
		return "redirect:/festival/readdetail/" + frid;
	}
	
	
	
	// Update
	/*@GetMapping("/update/{frid}")
	public String update(Model model,@PathVariable("frid") Integer frid) {
		model.addAttribute("Freview", freviewService.readdetail(frid));
		return "update";   // redirect 이 없으면 그냥 html을 호출
	}*/
	
	@PostMapping("/update")// Create @ModelAttribute 주로사용
	public String update(@ModelAttribute Freview freview) {
		freviewService.update(freview);
		return "redirect:readdeail/" + freview.getFrid();  // redirect 이 있으면 해당 매핑을 호출
	}

    // Delete
    @PostMapping("/freview/delete/{frid}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer fid) {
        freviewService.delete(fid);
        return ResponseEntity.ok().build();
    }
 // 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{frid}")
    public String freviewVote(Principal principal, @PathVariable("frid") Integer frid) {
        Freview freview = this.freviewService.getFreview(frid); // Freview로 
        Member member = this.memberService.getMember(principal.getName());
        this.freviewService.vote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", freview.getFestival().getFid());
    }

    // 비추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/devote/{frid}")
    public String freviewDevote(Principal principal, @PathVariable("frid") Integer frid) {
        Freview freview = this.freviewService.getFreview(frid); // 메소드 이름 일관성 유지
        Member member = this.memberService.getMember(principal.getName());
        this.freviewService.devote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", freview.getFestival().getFid());
    }
}