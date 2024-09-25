// 생산자 : 이진호
package com.mysite.portfolio.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/member")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/readdetail/{username}")
    public String readdetail(@PathVariable("username") String username, Model model) {
        // 사용자 정보 조회
        Optional<Member> memberOptional = memberService.findByUsername(username);
        
        if (memberOptional.isPresent()) {
            model.addAttribute("member", memberOptional.get());
            return "member/readdetail";  // Thymeleaf 템플릿
        } else {
            return "error";  // 사용자를 찾을 수 없을 때
        }
    }
	
	//회원 정보 수정
	@GetMapping("/update")
	public String update(Model model) {
		model.addAttribute("member", memberService.readdetail());
		return "member/update";
	}
	
	@PostMapping("/update/{username}")
	public String update(@ModelAttribute Member member) {
		memberService.update(member);
		return "redirect:/member/readdetail/" + member.getUsername();
	}
	
	//회원 탈퇴
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id,  HttpServletRequest request) {
		memberService.delete(id);
		
		// 로그아웃 처리
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate(); // 세션 무효화
	    }
		return "redirect:/signout";
	}
	
	//아이디 찾기
	@GetMapping("/idsearch")
    public String idsearch(Model model) {
		model.addAttribute("member", new Member());
        return "member/idsearch";
    }

	@PostMapping("/idsearch")
	public String idsearch(@ModelAttribute("maddr") Member member, Model model) {
		Optional<String> searchId = memberService.idsearch(member.getMaddr());

        if (searchId.isPresent()) {
            model.addAttribute("username", searchId.get());
        } else {
            model.addAttribute("username", "존재하지 않습니다.");
        }

        model.addAttribute("showModal", true);
        return "member/idsearch";
    }
	
}
