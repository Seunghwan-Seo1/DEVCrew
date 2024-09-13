// 생산자 : 이진호
package com.mysite.portfolio.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/memeber")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	//회원 정보 조회
	@GetMapping("/readdetail")
	public String readdetail(Model model) {
//		model.addAttribute("member", memberService.readdetail());
		return "member/readdetail";
	}
	
	//회원 정보 수정
	@GetMapping("/update/{id}")
	public String update(	@PathVariable("id") Integer id, 
							Model model
							) {
		model.addAttribute("member", memberService.readdetail());
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
