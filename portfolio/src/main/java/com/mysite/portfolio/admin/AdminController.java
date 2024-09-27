//서승환

package com.mysite.portfolio.admin;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.festival.Festival;
import com.mysite.portfolio.festival.FestivalService;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;
import com.mysite.portfolio.visitor.VisitorService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {
	
	private final MemberService memberService;
	
	private final FestivalService festivalService;
	
    private final VisitorService visitorService;

	



    @GetMapping("/main")
    public String showVisitorGraph(Model model) {
        List<Integer> dailyVisitorCounts = visitorService.getLast7DaysVisitorCounts(); // 최근 7일간의 방문자 수
        Integer totalVisitorCount = visitorService.getTotalVisitorCount(); // 총 방문자 수
        model.addAttribute("dailyVisitorCounts", dailyVisitorCounts);
        model.addAttribute("visitorCount", totalVisitorCount);
        return "admin/main"; // admin/main.html로 이동
    }
    
    @GetMapping("/notification")
    public String notification() {
    	return "admin/notification";
    }


	
	
	@GetMapping("/userconfig")
	public String member(Model model) {
		model.addAttribute("members", memberService.readlist());
		return "admin/userconfig";
	}

	@GetMapping("/festivalconfig")
	public String festival(Model model,
	                       @RequestParam(value = "search", required = false) String search,
	                       @RequestParam(value = "region", required = false, defaultValue = "전체") String region,
	                       @RequestParam(value = "category", required = false, defaultValue = "전체") String category) {
	    // 모든 축제를 가져오거나 필터링된 축제를 가져옵니다.
	    model.addAttribute("festivals", festivalService.filterFestivals(search, region, category));
	    return "admin/festivalconfig";
	}

	


	// 축제 업데이트 요청 처리
    @PostMapping("/festivalconfig")
    public String updateFestival(
        @RequestParam("fid") Integer fid, 
        @RequestParam("fname") String fname, 
        @RequestParam("flocation") String flocation,
        @RequestParam("files") List<MultipartFile> files, // 파일은 List로 받음
        Principal principal
    ) {
    	

    	
        try {
            // 기존 페스티벌 정보를 가져와서 수정
            Festival festival = festivalService.readdetail(fid); // ID로 기존 페스티벌 정보를 불러옴
            festival.setFname(fname); // 이름 업데이트
            festival.setFlocation(flocation); // 장소 업데이트

            // 파일과 기타 필드 업데이트
            festivalService.update(festival, files, memberService.getMember(principal.getName())); // 업데이트 메소드 호출

        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // 에러 페이지로 이동
        }

        return "redirect:/admin/festivalconfig"; // 성공 시 목록 페이지로 리다이렉트
    }
	
    // 게시글 삭제 메서드
    @PostMapping("/deleteFestival")
    public String deleteFestival(@RequestParam("fid") Integer fid) {
        festivalService.delete(fid);  // 사용자 삭제
        return "redirect:/admin/festivalconfig";  // 삭제 후 회원 목록으로 리다이렉트
    }
	
    @PostMapping("/updateRole")
    public String updateRole(@RequestParam("mid") Integer memberId,
                             @RequestParam("role") String newRole) {
        memberService.updateUserRole(memberId, newRole);
        return "redirect:/admin/userconfig";  // 변경 후 회원 목록으로 리다이렉트
    }
	
	// 사용자 삭제 메서드
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("mid") Integer mid) {
        memberService.delete(mid);  // 사용자 삭제
        return "redirect:/admin/userconfig";  // 삭제 후 회원 목록으로 리다이렉트
    }
    


	}


