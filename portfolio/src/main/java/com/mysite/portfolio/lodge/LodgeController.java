package com.mysite.portfolio.lodge;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lodge")
@RequiredArgsConstructor
@Controller
public class LodgeController {


	@Autowired
	private LodgeService lodgeService;
	
	@Autowired
	private MemberService memberService;
	
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
	public String lsecmain(Model model,
							@RequestParam(value = "search", required = false) String search,
				            @RequestParam(value = "region", required = false) String region,
	                       @RequestParam(value = "page", defaultValue = "0") int page,
	                       @RequestParam(value = "size", defaultValue = "8") int size) {
	    // 페이징 처리된 숙소 목록 가져오기
	    Page<Lodge> lodges = lodgeService.getLodgeListSortedByVotes(page, size);
	    
	    // 모델에 페이징된 숙소 리스트 및 페이지 정보 추가
	    model.addAttribute("lodges", lodges);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", lodges.getTotalPages());
	    model.addAttribute("downpath", "https://" + downpath);
	    
	    return "lodge/secondmain";
	}
	
	@GetMapping("/find")
    public String find(@RequestParam("keyword") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "8") int size,
                       Model model) {
        
        System.out.println("컨트롤러 : " + keyword);
        
        Page<Lodge> lodges = lodgeService.findByKeywordPaged(keyword, page, size); // 페이징된 검색 결과 가져오기
        model.addAttribute("lodges", lodges);
        model.addAttribute("keyword", keyword); // 검색어를 유지하기 위해 추가
        return "lodge/secondmain";
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
    public String update(@ModelAttribute Lodge lodge, @RequestParam("files") List<MultipartFile> files, Principal principal) throws IOException {
        lodgeService.update(lodge, files, memberService.getMember(principal.getName()));
        return "redirect:/lodge/detail/" + lodge.getLnum();
    }

	// delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        lodgeService.rvdelete(id);
        return "redirect:/lodge/secondmain";
    }
    
    //추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{lnum}")
    public String lodgeVote(Principal principal, @PathVariable("lnum") Integer lnum) {
        Lodge lodge = this.lodgeService.getLodge(lnum);
        Member member = this.memberService.getMember(principal.getName());
        this.lodgeService.vote(lodge, member);
        return String.format("redirect:/lodge/detail/%s", lnum);
    }
    
  //비추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/devote/{lnum}")
    public String lodgeDevote(Principal principal, @PathVariable("lnum") Integer lnum) {
        Lodge lodge = this.lodgeService.getLodge(lnum);
        Member member = this.memberService.getMember(principal.getName());
        this.lodgeService.devote(lodge, member);
        return String.format("redirect:/lodge/detail/%s", lnum);
    }
	
	
	
}