// 생산자 : 이진호
package com.mysite.portfolio.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberForm {

   @NotEmpty(message = "ID를 이메일 형식으로 입력해주세요")
   @Email
   private String username;
   
   @Pattern(regexp  = "^[a-zA-Z0-9!@#$%^&*]{4,16}$",
          message = "비밀번호는 4~16자의 영문 대소문자,숫자,특수문자로 구성되어야 합니다")
    private String password1;

    @NotEmpty(message = "비밀번호를 다시 입력해주세요")
    private String password2;
   
    @Size(min = 4, max = 10, message = "닉네임은 4~10자로 입력해주세요")
    private String nickname;
    
    @Pattern(regexp  = "^(010\\d{8}|(02|0(3[1-3]|4[1-4]|5[1-5]|6[1-4]))\\d{7,8})$",
           message = "-를 제외한 전화번호를 입력해주세요")
   private String maddr;
   
}
