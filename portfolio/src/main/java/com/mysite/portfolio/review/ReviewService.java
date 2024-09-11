package com.mysite.portfolio.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	//리뷰 작성
	public void create(Review review) {
		review.setUsername(null);
		
		reviewRepository.save(review);
	}
	
	
	//리뷰 리스트
	List<Review> rvlist() {
		return reviewRepository.findAll(); // 전체 목록 조회
	}
	
	
	//리뷰 수정
	public void rupdate(Review review) {
		reviewRepository.save(review);
	}
	
	
	//리뷰 삭제
	public void rdelete (Integer rnum) {
		reviewRepository.deleteById(rnum);
	}
	
}

