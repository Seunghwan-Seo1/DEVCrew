package com.mysite.portfolio.lodge;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysite.portfolio.festival.Festival;



public interface LodgeRepository extends JpaRepository<Lodge, Integer> {

	
	 @Query("SELECT l FROM Lodge l WHERE LOWER(l.lname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(l.llocation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	    List<Lodge> findAllByKeyword(@Param("keyword") String keyword);
	 Page<Lodge> findAll(Pageable pageable);
	 
	 @Query("SELECT l FROM Lodge l WHERE l.lname LIKE %:keyword% OR l.llocation LIKE :keyword%")
	 Page<Lodge> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable); // 페이징 적용된 검색 쿼리
	 
		@Query("SELECT l FROM Lodge l ORDER BY size(l.voter) DESC")
	    Page<Lodge> findAllByOrderByVoteCountDesc(Pageable pageable); // 추천수 기준 내림차순 + 페이징
		
		@Query("SELECT l FROM Lodge l ORDER BY size(l.voter) DESC")
	    List<Festival> findAllByOrderByVoteCountDesc(); // 추천 수 기준 내림차순 정렬
}
