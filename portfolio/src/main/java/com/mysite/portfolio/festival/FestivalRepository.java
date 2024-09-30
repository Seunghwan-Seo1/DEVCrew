//전현식
package com.mysite.portfolio.festival;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FestivalRepository extends JpaRepository<Festival, Integer> {
	
	@Query("SELECT f FROM Festival f ORDER BY size(f.voter) DESC")
    List<Festival> findAllByOrderByVoteCountDesc(); // 추천 수 기준 내림차순 정렬
	
	//검색 기능       
	@Query("SELECT f FROM Festival f WHERE f.fname LIKE %:keyword% OR f.flocation LIKE :keyword%")
    List<Festival> findAllByKeyword(@Param("keyword") String keyword);
	
	//페이징 기능
	Page<Festival> findAll(Pageable pageable);
}