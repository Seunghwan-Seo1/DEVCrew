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
    private String fname;
    private LocalDateTime fdate;
    private String flocation;
    private String fdescription;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fid;
    // 생성자, 게터 및 세터

    

   
}

