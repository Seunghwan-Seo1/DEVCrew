package com.mysite.portfolio.lodge;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.festival.Festival;

import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LodgeService {
	
	@Autowired
	private LodgeRepository lodgeRepository;
	
    @Autowired
    private S3Service s3Service;
    
	// 호출
	public List<Lodge> getList() {
		return this.lodgeRepository.findAll();
	}

	// 숙소 정보 작성
	public void lgcreate(Lodge lodge, MultipartFile file) throws IOException {
		String fileName = "";
        lodge.setRegiDate(LocalDateTime.now()); // 숙소 등록 일자로 데이터 등록
        
        UUID uuid = UUID.randomUUID();
		fileName = uuid + "_" + file.getOriginalFilename();
		s3Service.uploadFile(file, fileName);		
		lodge.setAccomm(fileName); // 건물 외관 사진
		
		this.lodgeRepository.save(lodge);
	}
	
	
	
	//숙소 목록
	public List<Lodge> lglist() {
		return lodgeRepository.findAll(); // 전체 목록 조회
	}
	
	
	
	// 숙소 상세보기
	public Lodge lgreaddetail(Integer lnum) {
		Optional<Lodge> ob = lodgeRepository.findById(lnum);
		return ob.get();
	}
	
	
	
	// 숙소 정보 수정
	public Lodge lgdetail(Integer lnum) {
		Optional<Lodge> ob = lodgeRepository.findById(lnum);
		return ob.get();
	}

	public void lgupdate(Lodge lodge) {
		lodgeRepository.save(lodge);
	}
	
	public void lgupdate(Lodge lodge, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
   
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            s3Service.uploadFile(file, fileName);
            lodge.setAccomm(fileName);
        } else {
            
            lodge.setAccomm(lodge.getAccomm());
        }
        lodgeRepository.save(lodge);
    }

	
	// 숙소 정보 삭제
	public void rvdelete(Integer lnum) {
		lodgeRepository.deleteById(lnum);
	}
}
