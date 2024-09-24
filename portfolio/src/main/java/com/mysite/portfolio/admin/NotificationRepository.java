package com.mysite.portfolio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.portfolio.member.Member;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
	
    List<Notification> findByRecipient(Member recipient);

}
