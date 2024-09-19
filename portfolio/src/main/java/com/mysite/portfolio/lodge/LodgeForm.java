package com.mysite.portfolio.lodge;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LodgeForm {
	
	@NotEmpty(message="숙소 이름은 필수항목입니다.")
	private String lname;
	
	@NotEmpty(message="숙소 안내 내용은 필수항목입니다.")
    private String linfo;

}
