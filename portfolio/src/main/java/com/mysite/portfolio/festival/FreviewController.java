package com.mysite.portfolio.festival;

import java.io.IOException;
import java.security.Principal;

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

import lombok.RequiredArgsConstructor;

@RequestMapping("/freview")
@RequiredArgsConstructor
@Controller
public class FreviewController {

	private final FreviewService freviewService;
	private Object memberService;
	
	
	//create
	
	@PostMapping("/create")
	public String create(@ModelAttribute Freview freview,
					     @RequestParam("fid") Integer fid
			) throws IOException {
		freviewService.create(freview, fid);
		return "redirect:/festival/readdetail/" + fid;
	}
	
	
	
	// Update
    @PostMapping("/modify/{fid}")
    public String update(@PathVariable("fid") Integer fid,
                         @ModelAttribute Freview freview,
                         @RequestParam("fid") Integer fid1) throws IOException {
        freviewService.update(fid1, freview);
        return "redirect:/festival/readdetail/" + fid1;
    }

    // Delete
    @PostMapping("/freview/delete/{fid}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer fid) {
        freviewService.delete(fid);
        return ResponseEntity.ok().build();
    }
 // 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{fid}")
    public String festivalVote(Principal principal, @PathVariable("fid") Integer fid) {
        Freview freview = this.freviewService.getFreview(fid); // Freview로 수정
        Class<? extends Object> member = this.memberService.getClass();
        this.freviewService.vote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", fid);
    }

    // 비추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/devote/{fid}")
    public String festivalDevote(Principal principal, @PathVariable("fid") Integer fid) {
        Freview freview = this.freviewService.getFreview(fid); // 메소드 이름 일관성 유지
        Class<? extends Object> member = this.memberService.getClass();
        this.freviewService.devote(freview, member);
        return String.format("redirect:/festival/readdetail/%s", fid);
    }
}