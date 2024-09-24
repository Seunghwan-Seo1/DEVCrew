package com.mysite.portfolio.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

@Service
public class NotificationService {
	@Autowired
    private NotificationRepository notificationRepository;
	
	@Autowired
    private MemberService memberService; // Member 정보를 가져오는 서비스가 있다고 가정

    

   

    // 특정 사용자의 알림 목록 가져오기
    public List<Notification> getNotificationsByRecipient(Member recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

        
    // ROLE_USER 사용자들에게 알림 보내기
    public void sendNotificationToUsers(String message) {
        List<Member> users = memberService.findByRole("ROLE_USER");
        for (Member user : users) {
            Notification notification = new Notification();
            notification.setRecipient(user);  // 알림 수신자 설정
            notification.setNmessage(message);
            notificationRepository.save(notification);  // 알림 저장
        }
    }
    
    // 특정 사용자의 알림을 조회
    public List<Notification> getNotificationsForUser(Member member) {
        return notificationRepository.findByRecipient(member);
    }
    
    // delete
    public void delete(Integer id) {
        notificationRepository.deleteById(id);
    }

    
    
 
}
