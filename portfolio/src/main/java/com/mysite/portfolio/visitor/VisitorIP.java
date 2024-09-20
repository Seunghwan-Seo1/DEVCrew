/*생산자 : 서승환
생산일 : 2024-09-19*/

package com.mysite.portfolio.visitor;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class VisitorIP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ipAddress;         // 방문자 IP
    private LocalDateTime lastVisit;  // 마지막 방문 시간

    // Getters and Setters
}

