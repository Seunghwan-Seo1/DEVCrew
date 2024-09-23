//전현식
package com.mysite.portfolio.festival;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FestivalRepository extends JpaRepository<Festival, Integer> {
	
	@Query("SELECT f FROM Festival f ORDER BY size(f.voter) DESC")
    List<Festival> findAllByOrderByVoteCountDesc(); // 추천 수 기준 내림차순 정렬
   
}