// 이유민
package com.mysite.portfolio.lodge;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Lodge {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer lnum; // 숙소 등록 번호
	private String lname; // 숙소 이름
	
	//숙소 유형
	private String motel;
	private String hotel;
	private String resort;
	private String geha;
	
	private Date ldate;
	
	// 숙소 필터 검색
	private Integer lprice; // 숙소 가격 
	private String breakfast; 
	private String swim; 
	private String wifi; 
	private String fitness; 
	private String bbq; 
	private String parking;
	
	private boolean discount; // 할인혜택있음
	
	// 상세보기 정보
	private String accomm;
	private String firstaccomm;
	private String secondaccomm;
	private String linfo;
	private String review;
	private String notice;
	

}
