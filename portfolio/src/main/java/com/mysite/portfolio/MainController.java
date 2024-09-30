package com.mysite.portfolio;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.portfolio.admin.Notification;
import com.mysite.portfolio.admin.NotificationService;
import com.mysite.portfolio.festival.FestivalService;
import com.mysite.portfolio.lodge.LodgeService;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

@Controller
public class MainController {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FestivalService festivalService;
	
	@Autowired
	private LodgeService lodgeService;
	
	
	@GetMapping("/")
	public String index(Model model,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "region", required = false, defaultValue = "전체") String region,
            @RequestParam(value = "category", required = false, defaultValue = "전체") String category) {
			// 모든 축제를 가져오거나 필터링된 축제를 가져옵니다.
			model.addAttribute("festivals", festivalService.filterFestivals(search, region, category));
			model.addAttribute("lodges", lodgeService.getList());
		
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
	
	@GetMapping("/ndetail/{nid}")
	public String getUserNotifications(Model model,@PathVariable("nid") String username) {
        
        // 해당 사용자의 알림 목록을 가져옴
        Member member = memberService.getMember(username);

        List<Notification> notifications = notificationService.getNotificationsByRecipient(member);
        
        // 알림 목록을 모델에 추가
        model.addAttribute("notifications", notifications);
        
        return "/ndetail"; 
    }
	
	 // 알림 삭제 요청 처리
    @PostMapping("/deletenoti/{nid}")
    public String deleteNotification(@PathVariable("nid") Integer nid, Principal principal) {
        notificationService.delete(nid);
        Member member = this.memberService.getMember(principal.getName());
        return String.format("redirect:/ndetail/%s", member.getUsername());  // 삭제 후 알림 목록 페이지로 리다이렉트
    }

}

