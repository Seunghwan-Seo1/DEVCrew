package com.mysite.portfolio.lodge;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.festival.Festival;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LodgeService {
	
	@Autowired
	private LodgeRepository lodgeRepository;
	
    @Autowired
    private S3Service s3Service;
    
    @Autowired
    private MemberRepository memberRepository;
    
	// 호출
	public List<Lodge> getList() {
		return this.lodgeRepository.findAll();
	}

	// 숙소 정보 작성
	public void lgcreate(Lodge lodge, List<MultipartFile> files, Principal principal) throws IOException {
		
        lodge.setRegiDate(LocalDateTime.now()); // 숙소 등록 일자로 데이터 등록
               
        Optional<Member> member = memberRepository.findByusername(principal.getName());
        lodge.setLwriter(member.get()); // optional 에서 진짜 작성자 객체를 빼내자
        
     // 파일 이름을 저장할 배열, 최대 5개
        String[] fileNames = new String[3];

        // 파일 업로드 처리 (빈 파일 건너뛰기)
        for (int i = 0; i < files.size() && i < 5; i++) {
            MultipartFile file = files.get(i);
            
            if (!file.isEmpty()) {
                try {
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid + "_" + file.getOriginalFilename();
                    s3Service.uploadFile(file, fileName);
                    fileNames[i] = fileName;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
                }
            } else {
                // 빈 파일 무시
                System.out.println("File " + i + " is empty and skipped.");
            }
        }

        // 파일 이름을 lodge 객체에 설정 (null 검증 추가)
        if (fileNames[0] != null) lodge.setAccomm(fileNames[0]);
        if (fileNames[1] != null) lodge.setFirstaccomm(fileNames[1]);
        if (fileNames[2] != null) lodge.setSecondaccomm(fileNames[2]);
       

        // 데이터 저장
        try {
            this.lodgeRepository.save(lodge);
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스 출력
            throw new RuntimeException("Failed to save lodge data.");
        }
	
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
	
	
	// 숙소 정보 검색
	 public List<Lodge> find(String keyword) {
	        System.out.println("서비스 : " + keyword);
	        return lodgeRepository.findAllByKeyword(keyword); // 수정된 메서드 호출
	    }
	 
	 public Page<Lodge> getLodges(int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        return lodgeRepository.findAll(pageable);
	    }
	 
	 public Page<Lodge> findByKeywordPaged(String keyword, int page, int size) {
	        Pageable pageable = PageRequest.of(page, size);
	        return lodgeRepository.findAllByKeyword(keyword, pageable); // 수정된 메서드 호출
	    }
	 
}
	
