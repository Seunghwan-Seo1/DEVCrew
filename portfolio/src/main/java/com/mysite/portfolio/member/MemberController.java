// 생산자 : 이진호
package com.mysite.portfolio.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;


@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	//회원 가입
	@GetMapping("/signup")
	public String signup(MemberForm memberForm) {
		return "signup";
	}
	@PostMapping("/signup")
	public String signup(	@Valid MemberForm memberForm,
							BindingResult bindingResult
							) {
		
		if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
        	memberService.create(memberForm);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("이미 등록된 사용자")) {
            	//아이디 중복 메세지
                bindingResult.rejectValue("username", "usernameExists", e.getMessage());
            } else if (e.getMessage().contains("2개의 패스워드")) {
            	//비밀번호 확인 메세지
                bindingResult.rejectValue("password2", "passwordMismatch", e.getMessage());
            } else {
                bindingResult.reject("signupFailed", e.getMessage());
            }
            return "signup";
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        } catch (Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup";
        }

		return "redirect:/signin";
	}
	
	//로그인
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
	
	//회원 정보 조회
	@GetMapping("/member/readdetail/{mid}")
	public String readdetail( 
								  @PathVariable("mid") Integer mid, Model model
								 
								) {
		model.addAttribute("member", memberService.readdetail(mid));
		return "member/readdetail";
	}
	
	//회원 정보 수정
	@GetMapping("/update/{id}")
	public String update(	@PathVariable("id") Integer id, 
							Model model
							) {
		model.addAttribute("member", memberService.readdetail(id));
		return "member/update";
	}
	
	@PostMapping("/update/{id}")
	public String update(@ModelAttribute Member member) {
		memberService.update(member);
		return "redirect:/readdail/" + member.getMid();
	}
	
	//회원 탈퇴
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		memberService.delete(id);
		return "redirect:/";
	}
	
}
