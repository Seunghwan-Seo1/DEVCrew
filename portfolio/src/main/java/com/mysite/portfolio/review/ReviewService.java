package com.mysite.portfolio.review;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;

import com.mysite.portfolio.member.MemberService;


@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private MemberService memberService;
	 

	// 리뷰 작성
//	public void rvcreate(Review review, MultipartFile file) throws IOException {
//		String fileName = "";
//		if (!file.isEmpty()) {
//			// 기본 사진 이름을 uuid 처리 후 aws에 저장
//			UUID uuid = UUID.randomUUID();
//			fileName = uuid + "_" + file.getOriginalFilename();
//			s3Service.uploadFile(file, fileName);
//		}
//		// 객체에 저장
//		review.setRpicture(fileName);
//		review.setRdate(LocalDateTime.now());
//
//		reviewRepository.save(review);
//
//	}

	public void rvcreate(Review review) {

		// 객체에 저장
		review.setRdate(LocalDateTime.now());

		reviewRepository.save(review);

	}
	

	//private final S3Service s3Service;
	//private final SiteUser siteUser;
	 

	// 리뷰 작성
	public void rvcreate(Review review, MultipartFile file) throws IOException {
		String fileName = "";
		if (!file.isEmpty()) {
			// 기본 사진 이름을 uuid 처리 후 aws에 저장
			UUID uuid = UUID.randomUUID();
			fileName = uuid + "_" + file.getOriginalFilename();
			//s3Service.uploadFile(file, fileName);
		}
		// 객체에 저장
		review.setRpicture(fileName);
		review.setRdate(LocalDateTime.now());

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


	public void rvupdate(Review review) {
		reviewRepository.save(review);
	}

	// 리뷰 삭제

	public void rvdelete(Integer uid) {
		reviewRepository.deleteById(uid);

	}

}
