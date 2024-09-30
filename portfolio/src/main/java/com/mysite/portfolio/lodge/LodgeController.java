package com.mysite.portfolio.lodge;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lodge")
@RequiredArgsConstructor
@Controller
public class LodgeController {


	@Autowired
	private LodgeService lodgeService;
	
	@Value("${cloud.aws.s3.endpoint}")
	private String downpath;
	

	// 숙박 메인페이지 (숙소 전체보기)
	@GetMapping("/main")
	public String lmain(Model model) {
		model.addAttribute("lodges", lodgeService.getList());
		model.addAttribute("downpath", "https://" + downpath);

		return "lodge/main";
	}

	// 상세 필터 검색 페이지 (지역별 보기)
	@GetMapping("/secondmain")
	public String lsecmain(Model model) {
		model.addAttribute("lodges", lodgeService.getList());
		model.addAttribute("downpath", "https://" + downpath);
		return "/lodge/secondmain";
	}


	// 숙박 상세 페이지 (정보, 리뷰, 안내사항 포함)
	@GetMapping("/detail/{lnum}")
	public String ldetail(@PathVariable ("lnum") Integer lnum, Model model) {
		model.addAttribute("lodge", lodgeService.lgreaddetail(lnum));

		model.addAttribute("downpath", "https://" + downpath);

		return "lodge/detail";
	}

	// 숙박 create
	@PreAuthorize("isAuthenticated()") // 로그인 해야 작성 가능
	@GetMapping("/lgcreate")
	public String lodgeCreate(LodgeForm lodgeForm) {
		return "lodge/lgcreate";
	}
	
	@PostMapping("/lgcreate")
	public String lgcreate(@ModelAttribute Lodge lodge, @RequestParam("files") List<MultipartFile> files, Principal principal) throws IOException {
		lodgeService.lgcreate(lodge, files, principal);

		return "redirect:/lodge/secondmain";

	}
	//alert 창 띄워주고 싶다
		

	// 숙박 read list
	@GetMapping("/lreadlist")
	public String lreadlist(Model model) {
		model.addAttribute("lodges", lodgeService.lglist());
		return "lodge/lreadlist";
	}
	
	// 숙박 read detail
	@GetMapping("/lreaddetail")
	public String lreaddetail() {
		return "lodge/lreaddetail";
	}

	// 숙박 update
	@GetMapping("/lupdate/{lnum}")
	public String lupdate(Model model, @PathVariable("lnum") Integer lnum) {
		model.addAttribute("lodge", lodgeService.lgreaddetail(lnum));
		return "lodge/lupdate";
	}

	@PostMapping("/lupdate")
	public String lupdate(@ModelAttribute Lodge lodge, MultipartFile file) throws IOException {
		lodgeService.lgupdate(lodge, file);
		return "lodge/lreaddetail/" + lodge.getLnum();
				
	}

	// 숙박 delete
	@GetMapping("/delete/{lnum}")
	public String delete(@PathVariable("lnum") Integer lnum) {

		return "redirect:/lodge/lreadlist";
	}
	
	//숙소 검색기능 추가	
	@GetMapping("/find")
    public String find(@RequestParam("keyword") String keyword, Model model) {
        System.out.println("컨트롤러 : " + keyword);
        
        // 앞의 5글자만 추출
        String processedKeyword = keyword.length() > 5 ? keyword.substring(0, 5) : keyword;
        String queryKeyword = "%" + processedKeyword + "%"; // % 추가
        System.out.println("처리된 키워드 : " + queryKeyword);

        model.addAttribute("lodges", lodgeService.find(queryKeyword)); // 수정된 키워드로 검색
        return "lodge/secondmain"; // lodge 뷰로 반환
    }
	
}