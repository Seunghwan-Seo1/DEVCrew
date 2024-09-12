//전현식
package com.mysite.portfolio.festival;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/festival")
@Controller
public class FestivalController {
	
	@Autowired
	private FestivalService festivalService;

	// create
	@GetMapping("/create")
	public String create() {        //축제 등록
		return "festival/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute Festival festival,
					     @RequestParam("file") MultipartFile file) throws IOException {
		festivalService.create(festival, file);
		return "redirect:/festival/readlist";
	}
	
	// readlist
	@GetMapping("/readlist")                      //축제목록
	public String readlist(Model model) {
		model.addAttribute("festivals", festivalService.readlist()); 
		return "festival/readlist";
	}
	

	
	// readdetail
	@GetMapping("/readdetail/{id}")
	public String readdetail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("festival", festivalService.readdetail(id));
		
		String downpath = "여기에 다운받을 경로를 설정하세요"; 
		model.addAttribute("downpath", "https://" + downpath);
		
		return "festival/readdetail";
	}
	
	//update
		@GetMapping("/update/{id}")
		public String update(Model model, @PathVariable("id") Integer id) {
			model.addAttribute("product", festivalService.readdetail(id));
			return "festival/update";
		}
		
		@PostMapping("/update")
		public String update(@ModelAttribute Festival festival, MultipartFile file) throws IOException {
			festivalService.update(festival, file);
			return "festival/readdetail/" + festival.getFid();
		}
		//delete
		@GetMapping("/delete/{id}")
		public String delete(@PathVariable("id") Integer id) {
			
			return "redirect:/festival/readlist";
		}
		
		// 게시글 삭제 메서드
	    @PostMapping("/deleteFestival")
	    public String deleteUser(@RequestParam("fid") Integer fid) {
	        festivalService.delete(fid);  // 사용자 삭제
	        return "redirect:/admin/festivalconfig";  // 삭제 후 회원 목록으로 리다이렉트
	    }
}
		

