package com.mysite.portfolio;

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
	
	@GetMapping("/ndetail/{username}")
	public String getUserNotifications(Model model,@PathVariable("username") String username) {
        
        // 해당 사용자의 알림 목록을 가져옴
        Member member = memberService.getMember(username);

        List<Notification> notifications = notificationService.getNotificationsByRecipient(member);
        
        // 알림 목록을 모델에 추가
        model.addAttribute("notifications", notifications);
        
        return "/ndetail"; 
    }

}

