//전현식
package com.mysite.portfolio.festival;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Festival {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    // 생성자, 게터 및 세터
    
    
    private String fname; //축제이름

    private String flocation;  //축제 장소
    
    @Column(length = 4000)
    private String fdescription; //축제설명
    
    private String fimg; //축제사진
    
    private Integer fprice; //축제참여비용
    
    


    
    private LocalDateTime fdate; //축제 날짜
   
}

