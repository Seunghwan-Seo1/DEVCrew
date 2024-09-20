package com.mysite.portfolio.festival;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequestMapping("/freview")
@RequiredArgsConstructor
@Controller
public class FreviewController {

	private final FreviewService freviewService;
	
	
	//c
	
	@PostMapping("/create")
	public String create(@ModelAttribute Freview freview,
					     @RequestParam("fid") Integer fid
			) throws IOException {
		freviewService.create(freview, fid);
		return "redirect:/festival/readdetail/" + fid;
	}
	
	
	
}
