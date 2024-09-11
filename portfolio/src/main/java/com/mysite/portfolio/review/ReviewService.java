package com.mysite.portfolio.review;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.portfolio.user.SiteUser;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	/*
	 * @Autowired private SiteUser siteUser;
	 */

	// 리뷰 작성
	public void rvcreate(Review review) {
		review.setUsername(null);

		reviewRepository.save(review);
	}

	// 리뷰 리스트
	List<Review> rvlist() {
		return reviewRepository.findAll(); // 전체 목록 조회
	}

	// 리뷰 수정
	public Review rvdetail(Integer uid) {
		Optional<Review> ob = reviewRepository.findById(uid);
		return ob.get();
	}

	public void rupdate(Review review) {
		reviewRepository.save(review);
	}

	// 리뷰 삭제
	public void rdelete(Integer uid) {
		reviewRepository.deleteById(uid);
	}

}
