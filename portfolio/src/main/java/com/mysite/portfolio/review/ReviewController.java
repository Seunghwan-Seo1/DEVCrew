package com.mysite.portfolio.review;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.lodge.LodgeService;
import com.mysite.portfolio.member.MemberService;


@RequestMapping("/review")
@Controller
public class ReviewController {

	@Autowired
	private LodgeService lodgeService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private S3Service s3Service;

	// 리뷰 기능

	@GetMapping("/detail")
	public String rvcreate() {
		return "lodge/detail";

	}

	@PostMapping("/detail/{mid}")

	public String rvcreate(@ModelAttribute Review review,
			@RequestParam MultipartFile file,
			@PathVariable Integer mid) throws IOException {
		// reviewService.rvcreate(review, file, mid);

		return "lodge/detail";
	}

	@PostMapping("/rvcreate/{lnum}")
	public String createReview(Model model, @PathVariable("lnum") Integer lnum,
			@RequestParam("rcontent") String rcontent) throws IOException {
		this.reviewService.rvcreate(lnum, rcontent);
		return String.format("redirect:/lodge/detail/%s", lnum);
	}

	// 리뷰 수정..

	@GetMapping("/detail/rvlist")
	public String rvlist(Model model) {
		model.addAttribute("reviewList", reviewService.rvlist());
		return "lodge/detail";
	}

	@GetMapping("/detail/{mid}")
	public String rvdetail(@PathVariable("mid") Integer mid, Model model) {
		model.addAttribute("lodge", reviewService.rvdetail(mid));
		return "lodge/detail";
	}

	@GetMapping("/detail/rvupdate/{mid}")
	public String rvupdate() {
		return "lodge/detail";
	}

	@PostMapping("/detail/rvupdate2/{mid}")
	public String rvupdate2(Model model, @PathVariable("mid") Integer id) {
		return "redirect:lodge/detail/" + id;
	}

	// 리뷰 삭제

	@GetMapping("/delete/{mid}")
	public String rvdelete(@PathVariable("mid") Integer mid) {
		reviewService.rvdelete(mid);
		return "redirect:lodge/detail";
	}


}