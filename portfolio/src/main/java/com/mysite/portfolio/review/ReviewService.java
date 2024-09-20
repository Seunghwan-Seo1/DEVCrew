package com.mysite.portfolio.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.portfolio.S3Service;

import com.mysite.portfolio.lodge.Lodge;
import com.mysite.portfolio.lodge.LodgeRepository;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {


	private final ReviewRepository reviewRepository;
	private final S3Service s3Service;
	private final MemberService memberService;
	private final LodgeRepository lodgeRepository;
	//private final SiteUser siteUser;
	 
	 
	// 리뷰 작성

	public void rvcreate(Integer lnum, String rcontent) throws IOException {

		// 객체에 저장		
		Review review = new Review();
		review.setRcontent(rcontent);;
		review.setRdate(LocalDateTime.now());
		
		Optional<Lodge> ol = lodgeRepository.findById(lnum);
		review.setLodge(ol.get());
		
		this.reviewRepository.save(review);
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

	public void rvupdate(Review review) {
		reviewRepository.save(review);
	}

	// 리뷰 삭제

	public void rvdelete(Integer uid) {
		reviewRepository.deleteById(uid);
	}

}
