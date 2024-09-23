package com.mysite.portfolio.festival;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Freview {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer frid;

	    @Column(columnDefinition = "TEXT")
	    private String frcontent;

	    private LocalDateTime frcreateDate; 

	    @ManyToOne
	    private Festival festival;  
	    
	    private LocalDateTime fmodifyDate;
	    
	 // Update 메서드
	    public void update(String newContent) {
	        this.frcontent = newContent;
	        this.fmodifyDate = LocalDateTime.now(); // 수정 시간 업데이트
	    }
	
	    
	    public void delete() {
	}


		public Object getSubject1() {
			// TODO Auto-generated method stub
			return null;
		}


		public void setSubject(Object subject) {
			// TODO Auto-generated method stub
			
		}


		public Object getSubject() {
			// TODO Auto-generated method stub
			return null;
		}

}
