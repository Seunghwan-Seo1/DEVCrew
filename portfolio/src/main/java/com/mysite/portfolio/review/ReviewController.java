package com.mysite.portfolio.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lodge")
@RequiredArgsConstructor
@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	/*
	 * @Autowired private S3Service s3Service;
	 */


	// 리뷰 기능
	@PostMapping("/lodge/detail/{uid}")
	public void rvcreate(@PathVariable("uid") Integer uid, @ModelAttribute Review review) {

		reviewService.rvcreate(review);
	}
	
	
	
	
	
	
	
	
}