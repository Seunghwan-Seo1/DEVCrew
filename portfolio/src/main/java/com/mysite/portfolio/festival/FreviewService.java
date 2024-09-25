package com.mysite.portfolio.festival;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.member.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreviewService {

	
	private final FreviewRepository freviewRepository;
	private final S3Service s3Service;
	private final MemberService memberService;
	private final FestivalRepository festivalRepository;
	
	
	//c  
	public void create(Freview freview, Integer fid) throws IOException {

		Optional<Festival> of = festivalRepository.findById(fid);
		freview.setFestival(of.get());		
		freview.setFrcreateDate(LocalDateTime.now());
		
		this.freviewRepository.save(freview);

	}
	
	//r
	public List<Freview> frvlist() {
		return freviewRepository.findAll();
	}
	
	//r
	
	
	// Update
    @Transactional
    public void update(Integer fid, Freview freview) {
        Freview existingFreview = freviewRepository.findById(fid)
            .orElseThrow(() -> new IllegalArgumentException("수정할 리뷰를 찾을 수 없습니다."));
        
        // 엔티티의 수정 메서드 호출
        existingFreview.update(freview.getFrcontent()); // freview의 content 설정
        existingFreview.setSubject(freview.getSubject()); // freview의 subject 설정
        
        freviewRepository.save(existingFreview);
    }

    // Delete
    @Transactional
    public void delete(Integer fid) {
        Freview existingFreview = freviewRepository.findById(fid)
            .orElseThrow(() -> new IllegalArgumentException("삭제할 리뷰를 찾을 수 없습니다."));
        
        freviewRepository.delete(existingFreview);
    }
}