package com.mysite.portfolio.lodge;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LodgeService {
	
	@Autowired
	private LodgeRepository lodgeRepository;


	//create
	public void lgcreate(Lodge lodge) {
		lodge.setRegiDate(LocalDateTime.now());
		
		lodgeRepository.save(lodge);
	}
	
	
	
	//read list
	List<Lodge> lglist() {
		return lodgeRepository.findAll(); // 전체 목록 조회
	}
	
	
	
	//read detail
	public Lodge lgreaddetail(Integer lnum) {
		Optional<Lodge> ob = lodgeRepository.findById(lnum);
		return ob.get();
	}
	
	
	
	//update
	public Lodge lgdetail(Integer lnum) {
		Optional<Lodge> ob = lodgeRepository.findById(lnum);
		return ob.get();
	}

	public void lgupdate(Lodge lodge) {
		lodgeRepository.save(lodge);
	}

	
	
	//delete
	public void rvdelete(Integer lnum) {
		lodgeRepository.deleteById(lnum);
	}
}
