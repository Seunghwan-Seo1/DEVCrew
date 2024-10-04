package com.mysite.portfolio.lodge;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Lodge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lnum; // 숙소 등록 번호

    private String lname; // 숙소 이름
    private LocalDateTime regiDate; // 숙소 등록 일자

    @ManyToOne
    private Member lwriter; // 작성자

    // 숙소 유형
    private String motel;
    private String hotel;
    private String resort;
    private String geha;

    private Date ldate; // 숙소 예약 날짜

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
    private String accomm; // 건물외관사진
    private String firstaccomm; // 건물 내부사진1
    private String secondaccomm; // 건물 내부사진2
    private String llocation; // 숙소 위치

    

    private String review;
    
    @Column(columnDefinition = "MEDIUMTEXT")
    private String linfo; // 숙소 정보

    @Column(columnDefinition = "MEDIUMTEXT")
    private String notice; // 공지사항


    @OneToMany(mappedBy = "lodge", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;
    
    @ManyToMany
    Set<Member> voter;
    
    @ManyToMany
    Set<Member> devoter;
    
 // 추천 수에서 비추천 수를 뺀 값 계산
    public int getVoteScore() {
        return this.voter.size() - this.devoter.size();
    }

}
