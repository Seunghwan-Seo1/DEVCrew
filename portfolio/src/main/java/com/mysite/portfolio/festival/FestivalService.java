package com.mysite.portfolio.festival;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FestivalService {
	
	@Autowired
	   private FestivalRepository festivalRepository;

	   //readlist
	   List<Festival> readlist() {
	      return festivalRepository.findAll();
	   }
	   
	   //readdetail
	   Festival readdetail(Integer id) {
	      Optional<Festival> ob = festivalRepository.findById(id);
	      return ob.get();
	   }
	   
	   //create
	   public void create(String fname, String flocation, String fdescription) {
	        Festival festival = new Festival();
	        festival.setFname(fname);
	        festival.setFlocation(flocation);
	        festival.setFdescription(fdescription);
	        festival.setFdate(LocalDateTime.now());
	        
	        
	        this.festivalRepository.save(festival);

}
}
