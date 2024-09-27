package com.mysite.portfolio.lodge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface LodgeRepository extends JpaRepository<Lodge, Integer> {

	
	 @Query("SELECT l FROM Lodge l WHERE LOWER(l.lname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(l.llocation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<Lodge> findAllByKeyword(@Param("keyword") String keyword);
	
}
