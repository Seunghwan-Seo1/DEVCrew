package com.mysite.portfolio.admin;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.mysite.portfolio.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nid;  // 알림 고유 ID

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Member recipient;  // 수신자 (사용자)

    private String nmessage;  // 알림 메시지 내용
    private Boolean isRead = false;  // 알림이 읽혔는지 여부

    @CreationTimestamp
    private LocalDateTime ntime;  // 알림 생성 시간
}
