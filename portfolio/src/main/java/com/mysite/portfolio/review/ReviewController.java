package com.mysite.portfolio.review;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.lodge.LodgeService;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import jakarta.validation.Valid;

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
	public String rvcreate(@ModelAttribute Review review, @RequestParam MultipartFile file, @PathVariable Integer mid)
			throws IOException {
		// reviewService.rvcreate(review, file, mid);

		return "lodge/detail";
	}

	@PostMapping("/rvcreate/{lnum}")
	public String createReview(Model model, @PathVariable("lnum") Integer lnum,
			@RequestParam("rcontent") String rcontent, Principal principal) throws IOException {
		this.reviewService.rvcreate(lnum, rcontent);
		return String.format("redirect:/lodge/detail/%s", lnum);
	}

	// 리뷰 수정..
//	@GetMapping("/detail/rvlist")
//	public String rvlist(Model model) {
//		model.addAttribute("reviewList", reviewService.rvlist());
//		return "lodge/detail";
//	}

	@GetMapping("/detail/rvupdate/{mid}")
	public String rvdetail(@PathVariable("mid") Integer mid, Model model) {
		model.addAttribute("lodge", reviewService.rvdetail(mid));
		return "lodge/detail";
	}

//	@GetMapping("/detail/rvupdate/{mid}")
//	public String rvupdate() {
//		return "lodge/detail";
//	}

	
//	  @PostMapping("/detail/rvupdate2/{mid}") 
//	  public String rvupdate2(Model model, @PathVariable("mid") Integer id) { 
//		  return "redirect:lodge/detail/" +mid; 
//	  }
		
	//작성자만 수정할 수 있게
	/*
	 * @PreAuthorize("isAuthenticated()")
	 * 
	 * @PostMapping("/detail/rvupdate/{mid}") public String rvupdate(@Valid
	 * ReviewForm reviewForm, BindingResult bindingResult,
	 * 
	 * @PathVariable("mid") Integer rnum, Principal principal) { if
	 * (bindingResult.hasErrors()) { return "review_form"; } Review review =
	 * this.reviewService.getReview(rnum); if
	 * (!review.getUsername().equals(principal.getName())) { throw new
	 * ResponseStatusException(HttpStatus.BAD_REQUEST, "자신이 쓴 리뷰만 수정이 가능합니다."); }
	 * this.reviewService.rvupdate(review); return
	 * String.format("redirect:/lodge/detail/%s", review.getLodge().getLnum()); }
	 */

	//리뷰 수정
	@PostMapping("/rvtest")
	public String rvupdate(@ModelAttribute Review review,
						   @RequestParam("lid") Integer lid
			) {
			this.reviewService.rvupdate(review, lid);
		
		return "redirect:/lodge/detail/" + lid;
	}
	 
	 
	 	 
	// 리뷰 삭제
//	@GetMapping("/delete/{mid}")
//	public String rvdelete(@PathVariable("mid") Integer mid) {
//		reviewService.rvdelete(mid);
//		return "redirect:lodge/detail";
//	}
	
	// /review/delete/${review.rnum}/${lodge.lnum}|}
	// http://localhost:8090/review/delete/24/lodge/detail/11
	 @PreAuthorize("isAuthenticated()")
	 @GetMapping("/delete/{rnum}")
	public String rvdelete(@PathVariable("rnum") Integer rnum
			) {
		Review review = reviewService.rvdetail(rnum);
		Integer lnum = review.getLodge().getLnum();
		reviewService.rvdelete(rnum);
		return "redirect:/lodge/detail/" + lnum;
	}
	
		
		
	// 공감
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/ragree/{rnum}")
	public String reviewAgr(Principal principal, @PathVariable("rnum") Integer mid) {
		Review review = this.reviewService.getReview(mid);
		Member member = this.memberService.getMember(principal.getName());
		this.reviewService.agree(review, member);
		return String.format("redirect:/lodge/detail/%s", review.getLodge().getLnum());
	}

	// 비공감
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/rdisagree/{rnum}")
	public String reviewDagr(Principal principal, @PathVariable("rnum") Integer mid) {
		Review review = this.reviewService.getReview(mid);
		Member member = this.memberService.getMember(principal.getName());
		this.reviewService.disagree(review, member);
		return String.format("redirect:/lodge/detail/%s", review.getLodge().getLnum());
	}

	// 리뷰 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{rnum}")
	public String rvdelete(@PathVariable("rnum") Integer rnum) {
		Review review = reviewService.rvdetail(rnum);
		Integer lnum = review.getLodge().getLnum();
		reviewService.rvdelete(rnum);
		return "redirect:/lodge/detail/" + lnum;
	}

//	// 공감
//	@PreAuthorize("isAuthenticated()")
//	@GetMapping("/ragree/{rnum}")
//	public String reviewAgr(Principal principal, @PathVariable("rnum") Integer mid) {
//		Review review = this.reviewService.getReview(mid);
//		Member member = this.memberService.getMember(principal.getName());
//		this.reviewService.agree(review, member);
//		return String.format("redirect:/lodge/detail/%s", review.getLodge().getLnum());
//	}
//
//	// 비공감
//	@PreAuthorize("isAuthenticated()")
//	@GetMapping("/rdisagree/{rnum}")
//	public String reviewDagr(Principal principal, @PathVariable("rnum") Integer mid) {
//		Review review = this.reviewService.getReview(mid);
//		Member member = this.memberService.getMember(principal.getName());
//		this.reviewService.disagree(review, member);
//		return String.format("redirect:/lodge/detail/%s", review.getLodge().getLnum());
//	}

}