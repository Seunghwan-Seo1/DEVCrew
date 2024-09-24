package com.mysite.portfolio.festival;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.portfolio.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	    
	 

	    @ManyToOne
	    private Member author;
	    
	    @ManyToMany
	    Set<Member> voter;
	    
	    @ManyToMany
	    Set<Member> devoter;
	    
	 // 추천 수에서 비추천 수를 뺀 값 계산
	    public int getVoteScore() {
	        return this.voter.size() - this.devoter.size();
	    }
	    
}
