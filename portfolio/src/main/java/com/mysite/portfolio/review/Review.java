// 이유민

package com.mysite.portfolio.review;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.portfolio.lodge.Lodge;
import com.mysite.portfolio.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rnum; //리뷰 번호
	
	private String username; // 사용자
	private String rname; // 리뷰 작성자
	private String rcontent; //리뷰 내용
	private String rstar; // 별점
	private String rpicture; // 리뷰 사진등록
	private LocalDateTime rdate; // 작성일자
	
	@ManyToMany
	Set<Member> ragree; // 공감
	@ManyToMany
	Set<Member> rdisagree; // 비공감
		
	@ManyToOne
	private Lodge lodge;
		
	@ManyToOne
	private Member member;
	
	
	

}
