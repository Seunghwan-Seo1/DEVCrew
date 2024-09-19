package com.mysite.portfolio.visitor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
	
	@Query("SELECT SUM(v.vcount) FROM Visitor v") // vcount의 총합을 가져오는 쿼리
    Integer getTotalVisitorCount();

}
