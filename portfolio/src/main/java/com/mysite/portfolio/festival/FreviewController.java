package com.mysite.portfolio.festival;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
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
	
	
	//u
	// Update
    @PostMapping("/modify/{fid}")
    public String update(@PathVariable("fid") Integer fid,
                         @ModelAttribute Freview freview,
                         @RequestParam("fid") Integer fid1) throws IOException {
        freviewService.update(fid1, freview);
        return "redirect:/festival/readdetail/" + fid1;
    }

    // Delete
    @PostMapping("/freview/delete/{fid}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer fid) {
        freviewService.delete(fid);
        return ResponseEntity.ok().build();
    }

}
