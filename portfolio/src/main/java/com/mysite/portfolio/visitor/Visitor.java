package com.mysite.portfolio.visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vid;
    private Integer vcount;
    private LocalDateTime vdate;

    // 방문 날짜 추가
    private LocalDate visitDate; // 추가된 필드
}
