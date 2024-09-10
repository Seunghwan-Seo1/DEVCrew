// 이유민

package com.mysite.portfolio.review;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rnum; //리뷰 번호
	
	private String username; // 작성자
	private String rcontent; //리뷰 내용
	private String rstar; // 별점
	private String rpicture; // 리뷰 사진등록
	private LocalDateTime rdate; // 작성일자
	
	private String ragree; // 공감
	private String rdisagree; // 비공감

}
