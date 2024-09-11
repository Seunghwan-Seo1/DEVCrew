package com.mysite.portfolio.lodge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LodgeService {
	
	@Autowired
	private LodgeRepository lodgeRepository;
	
	// 숙소 리스트
	List<Lodge> lglist() {
		return lodgeRepository.findAll();
	}

}
