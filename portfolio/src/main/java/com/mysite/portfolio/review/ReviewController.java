package com.mysite.portfolio.review;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.user.UserService;

@RequestMapping("/lodge")
@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private S3Service s3Service;
	


	// 리뷰 기능
	@GetMapping("/lodge/detail/")
	public String rvcreate() {
		return "lodge/detail";
	}
	
	@PostMapping("/lodge/detail/")
	public String rvcreate(@ModelAttribute Review review, MultipartFile file)  throws IOException {
		reviewService.rvcreate(review, file);
		return "lodge/detail";

		
	}
	
	
	
	
	
	
	
	
	
}