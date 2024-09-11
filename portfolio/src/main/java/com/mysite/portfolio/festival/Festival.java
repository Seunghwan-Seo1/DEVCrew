//전현식
package com.mysite.portfolio.festival;

import java.time.LocalDateTime;

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
    
    
    private String fname;

    private String flocation;
    private String fdescription;
    private String fimg;
    private String fprice;
    
    


    
    private LocalDateTime fdate;
   
}

