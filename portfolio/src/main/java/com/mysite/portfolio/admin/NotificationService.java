package com.mysite.portfolio.admin;

import java.util.List;
import java.util.Optional;

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
    
    public void sendNotificationToUserByUsername(String username, String message) {
        // username으로 Member 객체 찾기 (Optional로 반환)
        Optional<Member> optionalRecipient = memberService.findByUsername(username);
        
        if (optionalRecipient.isPresent()) {  // 사용자가 존재하는지 확인
            Member recipient = optionalRecipient.get();  // Optional에서 Member 객체 추출
            Notification notification = new Notification();
            notification.setRecipient(recipient);  // 특정 알림 수신자 설정
            notification.setNmessage(message);
            notificationRepository.save(notification);  // 알림 저장
        } else {
            // 사용자 찾기 실패 시 로직 (예: 오류 메시지 출력)
            System.out.println("사용자를 찾을 수 없습니다: " + username);
        }
    }

    
    
    // delete
    public void delete(Integer nid) {
        
            notificationRepository.deleteById(nid);
        
    }
    
    
 
}
