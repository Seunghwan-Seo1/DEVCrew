package com.mysite.portfolio.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.portfolio.DataNotFoundException;
import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.festival.Freview;
import com.mysite.portfolio.lodge.Lodge;
import com.mysite.portfolio.lodge.LodgeRepository;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final S3Service s3Service;
	private final MemberService memberService;
	private final LodgeRepository lodgeRepository;

	// 리뷰 작성

	public void rvcreate(Integer lnum, String rcontent) throws IOException {

		// 객체에 저장
		Review review = new Review();
		review.setRcontent(rcontent);
		review.setMember(memberService.readdetail());
		review.setUsername(memberService.readdetail().getUsername());
		review.setRdate(LocalDateTime.now());

		Optional<Lodge> ol = lodgeRepository.findById(lnum);
		review.setLodge(ol.get());

		this.reviewRepository.save(review);
	}

	// 리뷰 리스트
	List<Review> rvlist() {
		return reviewRepository.findAll(); // 전체 목록 조회
	}

	// 리뷰 수정 (단건)
	public Review rvdetail(Integer rnum) {
		Optional<Review> ob = reviewRepository.findById(rnum);
		return ob.get();

	}
	
	// 리뷰 수정
//	public void rvupdate(Review review) {
//		reviewRepository.save(review);
//	}

//	public void modify(Review review, String rcontent) {
//		review.setRcontent(rcontent);
//		review.setRdate(LocalDateTime.now());
//		this.reviewRepository.save(review);
//	}

	public void updateRv(Review review) {
		Optional<Review> _review = reviewRepository.findById(review.getRnum());
		
		Review reviewData = _review.get();
		reviewData.setRcontent(review.getRcontent());
		this.reviewRepository.save(reviewData);
	}
	
	
	
	public Review getReview(Integer rnum) {
		Optional<Review> review = this.reviewRepository.findById(rnum);
		if (review.isPresent()) {
			return review.get();
		} else {
			throw new DataNotFoundException("등록한 리뷰가 없습니다");
		}
	}
	
	// 리뷰 삭제
	public void rvdelete(Integer mid) {
		this.reviewRepository.deleteById(mid);
	}
	
	// 공감
	public void agree(Review review, Member member) {
		review.getRagree().add(member);
		this.reviewRepository.save(review);
	}
	
	// 비공감
	public void disagree(Review review, Member member) {
		review.getRdisagree().add(member);
		this.reviewRepository.save(review);
	}
	
	

}
