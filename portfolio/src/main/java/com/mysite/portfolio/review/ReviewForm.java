package com.mysite.portfolio.review;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReviewForm {
	
	@NotEmpty(message = "리뷰 내용과 별점은 필수 입력입니다")

	private String rcontent;

}
