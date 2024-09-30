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
	public String update(@PathVariable("frid") Integer frid, 
            @RequestParam("frcontent") String frcontent, @RequestParam("fid") Integer fid,
            Model model, Principal principal) {
			// Freview 객체 생성 및 업데이트할 내용 설정
			Freview freview = new Freview();
			freview.setFrid(frid);          // PathVariable로 받아온 리뷰 ID 설정
			freview.setFrcontent(frcontent); // RequestParam으로 받아온 새로운 리뷰 내용 설정
			
			// 서비스 레이어를 통해 업데이트 처리
			freviewService.update(freview,  memberService.getMember(principal.getName()));
		
    	return "redirect:/festival/readdetail/" + fid;
	}
	

    // Delete
	    @PostMapping("/delete/{frid}")
	    public String deleteFreview(@PathVariable("frid") Integer frid, 
                @RequestParam("fid") Integer fid  // 페스티벌 ID를 받아서 리다이렉트할 때 사용
                ) {
				// 리뷰 삭제 서비스 호출
				freviewService.delete(frid);
				
				return "redirect:/festival/readdetail/" + fid;
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