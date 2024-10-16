package com.mysite.portfolio.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;


@RequestMapping("/admin/notification")
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
  
    @Autowired
    private MemberService memberService;

    
    //알림 유저에게 발송
    @PostMapping("/sendNotification")
    public String sendNotificationToUsers(@RequestParam("message") String message) {
        notificationService.sendNotificationToUsers(message);
        return "redirect:/admin/notification";
    }
    
 // 특정 사용자에게 알림 발송
    @PostMapping("/sendNotificationToUser")
    public String sendNotificationToUser(
            @RequestParam("username") String username, 
            @RequestParam("message") String message) {
        
        // NotificationService를 통해 알림 발송
        notificationService.sendNotificationToUserByUsername(username, message);
        
        return "redirect:/admin/notification";  // 알림 후 관리자 페이지로 리다이렉트
    }

    
    @GetMapping("/notifications")
    public String getNotifications(Model model, Principal principal) {
        Member member = memberService.getMember(principal.getName()); // 현재 로그인된 사용자 가져오기
        model.addAttribute("notifications", notificationService.getNotificationsByRecipient(member));
        return "notification/list"; // 알림 목록을 보여줄 뷰 페이지
    }
    
    
}
