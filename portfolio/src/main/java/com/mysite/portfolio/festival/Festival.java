// 전현식
package com.mysite.portfolio.festival;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.portfolio.member.Member;

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
public class Festival {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    
    private String fname; // 축제이름
    private String flocation;  // 축제 장소
    
    @Column(length = 4000)
    private String fdescription; // 축제 설명

    private String fcategory; // 카테고리 
    private String fimg; // 축제 사진 메인
    private String fimg2; // 추가 사진 1
    private String fimg3; // 추가 사진 2
    private String fimg4; // 추가 사진 3
    private String fimg5; // 추가 사진 4
    private Integer fprice; // 축제 참여 비용
    private LocalDateTime fdate; // 축제 날짜
    
    @OneToMany(mappedBy = "festival", cascade = CascadeType.REMOVE)
    private List<Freview> freviewList;
    
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
