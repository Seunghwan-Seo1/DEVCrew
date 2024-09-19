package com.mysite.portfolio.visitor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    
    @Query("SELECT v FROM Visitor v WHERE v.visitDate = :date")
    List<Visitor> findVisitorsByVisitDate(@Param("date") LocalDate date);
    
    @Query("SELECT COUNT(v) FROM Visitor v WHERE v.visitDate = :date")
    Integer countVisitorsByVisitDate(@Param("date") LocalDate date);  // 날짜별 방문자 수를 세는 메소드

    @Query("SELECT v FROM Visitor v ORDER BY v.visitDate")
    List<Visitor> findAllVisitors();  // 모든 방문자 데이터 조회
    
    @Query("SELECT SUM(v.vcount) FROM Visitor v")
    Integer getTotalVisitorCount(); // 총 방문자 수 계산
}
