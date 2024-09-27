package com.mysite.portfolio.festival;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mysite.portfolio.S3Service;
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
    
    @Autowired
    private S3Service s3Service; // S3Service 주입
    
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
                           @RequestParam(value = "region", required = false, defaultValue = "전체") String region,
                           @RequestParam(value = "category", required = false, defaultValue = "전체") String category) {
        List<Festival> festivals = festivalService.filterFestivals(search, region, category); // 필터 메서드 호출
        model.addAttribute("festivals", festivals);
        return "festival/readlist";
    }
    
    //검색 기능 추가
    
    @GetMapping("/find")
    public String find(@RequestParam("keyword") String keyword, Model model) {
    	System.out.println("컨트롤러 : " + keyword);
    	model.addAttribute("festivals", festivalService.find(keyword));
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

