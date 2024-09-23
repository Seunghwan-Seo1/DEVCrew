package com.mysite.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailsender;
	
	public void create(String title, String content, String senderName) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		

			message.setFrom("oosssok1@naver.com");
	        message.setTo("oosssok1@naver.com");
	        message.setSubject(title);
	        message.setText(String.format("From: %s\n\n%s", senderName, content));  // 이메일 본문에 발신자 이름 추가
	        
	        mailsender.send(message);
	    }

	
	

}

