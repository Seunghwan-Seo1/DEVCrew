package com.mysite.portfolio.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberRepository;
import com.mysite.portfolio.member.MemberService;


@RequestMapping("/admin/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private MemberRepository memberRepository;
    
    
    @Autowired
    private MemberService memberService;

    

    // 특정 사용자의 알림 목록 조회
    @GetMapping("/member/{memberId}")
    public List<Notification> getmemberNotifications(@PathVariable Integer memberId) {
        Member recipient = memberRepository.findById(memberId)
                                       .orElseThrow(() -> new RuntimeException("member not found"));
        return notificationService.getNotificationsByRecipient(recipient);
    }

    // 알림 삭제
    @PostMapping("/delete/{notificationId}")
    public ResponseEntity<?> deleteNotification(@RequestParam("nid") Integer nid) {
        try {
            notificationService.delete(nid);  // 알림 삭제
            return ResponseEntity.ok("Notification deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting notification");
        }
    }
    
    //알림 유저에게 발송
    @PostMapping("/sendNotification")
    public String sendNotificationToUsers(@RequestParam("message") String message) {
        notificationService.sendNotificationToUsers(message);
        return "redirect:/admin/notification";
    }
    
    @GetMapping("/notifications")
    public String getNotifications(Model model, Principal principal) {
        Member member = memberService.getMember(principal.getName()); // 현재 로그인된 사용자 가져오기
        model.addAttribute("notifications", notificationService.getNotificationsForUser(member));
        return "notification/list"; // 알림 목록을 보여줄 뷰 페이지
    }
    
    
}
