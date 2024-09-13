package com.mysite.portfolio.festival;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FestivalService {
    
    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private S3Service s3Service; // S3Service 주입

    // create
    public void create(Festival festival, MultipartFile file) throws IOException {
    	String fileName = "";
        festival.setFdate(LocalDateTime.now()); // 데이터를 등록
      //기본 사진이름을 uuid 처리 후 aws에 저장
		UUID uuid = UUID.randomUUID();
		fileName = uuid + "_" + file.getOriginalFilename();
		s3Service.uploadFile(file, fileName);
		
		festival.setFimg(fileName);
    
        this.festivalRepository.save(festival);
    }

    
    
    

    // readlist
    public List<Festival> readlist() {
        return festivalRepository.findAll();
    }

   // readdetail
	
	/*
	 * public Festival readdetail(Integer fid) { return
	 * festivalRepository.findById(fid) .orElseThrow(() -> new
	 * RuntimeException("Festival not found")); }
	 */
	
	  public Festival readdetail(Integer fid) {
		  Optional<Festival> ob =festivalRepository.findById(fid); 
		  return ob.get(); }
	
    
    
    

    // update
    public void update(Festival festival, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            // 새 사진을 넣을 경우
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + "_" + file.getOriginalFilename();
            s3Service.uploadFile(file, fileName);
            festival.setFimg(fileName); // 새로운 uuid 붙인 사진 넣기
        } else {
            // 기존 사진 이름을 그대로 사용
            festival.setFimg(festival.getFimg());
        }
        festivalRepository.save(festival);
    }

    // delete
    public void delete(Integer id) {
        festivalRepository.deleteById(id);
    }
}