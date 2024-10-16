package com.mysite.portfolio.festival;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RequestMapping("/festival")
@RequiredArgsConstructor
@Controller
public class FestivalController {
    
    @Autowired
    private FestivalService festivalService;

    @Autowired
	private MemberService memberService;
    

    
    // create
    @GetMapping("/create")
    public String create() { // 축제 등록
        return "festival/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Festival festival,
                         @RequestParam("files") List<MultipartFile> files, Principal principal) throws IOException {
    	if (files.size() > 0 && files.get(0) != null) {
        festivalService.create(festival, files, memberService.getMember(principal.getName()));
    	}
        return "redirect:/festival/readlist";
    }

    
    //readlist
    @GetMapping("/readlist")
    public String readlist(Model model, 
                           @RequestParam(value = "search", required = false) String search,
                           @RequestParam(value = "region", required = false) String region,
                           @RequestParam(value = "page", defaultValue = "0") int page,  // 페이지 번호
                           @RequestParam(value = "size", defaultValue = "8") int size) {  // 페이지 크기

        Page<Festival> festivalPage = festivalService.getFestivalListSortedByVotes(page, size);
        
        // 전체 페이지 수 구하기
        int totalPages = festivalPage.getTotalPages();
        
        // 현재 페이지 값을 모델에 추가
        model.addAttribute("festivalPage", festivalPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);  // 현재 페이지 추가

        return "festival/readlist";
    }

    
    //검색 기능 추가
    
    @GetMapping("/find")
    public String find(@RequestParam("keyword") String keyword,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "8") int size,
                       Model model) {
        
        System.out.println("컨트롤러 : " + keyword);
        
        Page<Festival> festivalPage = festivalService.findByKeywordPaged(keyword, page, size); // 페이징된 검색 결과 가져오기
        model.addAttribute("festivalPage", festivalPage);
        model.addAttribute("keyword", keyword); // 검색어를 유지하기 위해 추가
        return "festival/readlist";
    }

    // readdetail
    @GetMapping("/readdetail/{id}") // 축제 내용 상세보기 
    public String readdetail(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("festival", festivalService.readdetail(id));
        return "festival/readdetail";
    }

    // update
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("festival", festivalService.readdetail(id));
        return "festival/update";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute Festival festival, @RequestParam("files") List<MultipartFile> files, Principal principal) throws IOException {
        festivalService.update(festival, files, memberService.getMember(principal.getName()));
        return "redirect:/festival/readdetail/" + festival.getFid();
    }

    // delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        festivalService.delete(id);
        return "redirect:/festival/readlist";
    }

    //추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{fid}")
    public String festivalVote(Principal principal, @PathVariable("fid") Integer fid) {
        Festival festival = this.festivalService.getFestival(fid);
        Member member = this.memberService.getMember(principal.getName());
        this.festivalService.vote(festival, member);
        return String.format("redirect:/festival/readdetail/%s", fid);
    }
    
  //비추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/devote/{fid}")
    public String festivalDevote(Principal principal, @PathVariable("fid") Integer fid) {
        Festival festival = this.festivalService.getFestival(fid);
        Member member = this.memberService.getMember(principal.getName());
        this.festivalService.devote(festival, member);
        return String.format("redirect:/festival/readdetail/%s", fid);
    }
    
   

}
