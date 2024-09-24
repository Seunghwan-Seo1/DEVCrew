package com.mysite.portfolio;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mysite.portfolio.admin.Notification;
import com.mysite.portfolio.admin.NotificationService;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final NotificationService notificationService;
    private final MemberService memberService;

   
    public GlobalControllerAdvice(NotificationService notificationService, MemberService memberService) {
        this.notificationService = notificationService;
        this.memberService = memberService;
    }

    // 모든 요청에서 사용자 알림을 가져와 모델에 추가
    @ModelAttribute("notifications")
    public List<Notification> addNotificationsToModel(Principal principal) {
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            return notificationService.getNotificationsForUser(member);
        }
        return null; // 로그인되지 않은 경우 빈 리스트 또는 null 반환
    }
}
