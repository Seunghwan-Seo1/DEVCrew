//전현식
package com.mysite.portfolio.festival;

import java.time.LocalDateTime;
import java.util.List;



import com.mysite.portfolio.review.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Festival {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    // 생성자, 게터 및 세터
    
    private String username;  // 회원 아이디, 이메일 주소로 하면 많이 편하다. 
    
    private String fname; //축제이름

    private String flocation;  //축제 장소
    
    @Column(length = 4000)
    private String fdescription; //축제설명

    
    
   
    private String fimg; //축제사진 메인
    
    private String fimg2; // 추가 사진 1
    
    private String fimg3; // 추가 사진 2
    
    private String fimg4; // 추가 사진 3
    
    private String fimg5; // 추가 사진 4
    
    
      
    
    private Integer fprice; //축제참여비용
     
    
    private LocalDateTime fdate; //축제 날짜
   
    @OneToMany(mappedBy = "festival", cascade = CascadeType.REMOVE)
	private List<Freview> freviewList; 
    
}

