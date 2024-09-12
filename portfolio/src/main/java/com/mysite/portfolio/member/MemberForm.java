// 생산자 : 이진호
package com.mysite.portfolio.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberForm {

	@NotEmpty(message = "ID를 이메일 형식으로 입력해주세요")
	@Email
	private String username;
	
	@NotEmpty(message = "비밀번호를 입력해주세요")
    private String password1;

    @NotEmpty(message = "비밀번호 확인을 부탁드립니다")
    private String password2;
	
    @NotEmpty(message = "연락처를 입력해주세요")
	private String maddr;
	
}
