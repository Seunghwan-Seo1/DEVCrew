package com.mysite.portfolio.festival;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreviewService {

	@Autowired
	private final FreviewRepository freviewRepository;
	private final S3Service s3Service;
	private final MemberService memberService;
	private final FestivalRepository festivalRepository;
	
	
	//c  
	public void create(Freview freview, Integer frid) throws IOException {

		Optional<Festival> of = festivalRepository.findById(frid);
		freview.setFestival(of.get());		
		freview.setFrcreateDate(LocalDateTime.now());
		
		this.freviewRepository.save(freview);

	}
	
	//r
	public List<Freview> frvlist() {
		return freviewRepository.findAll();
	}
	
	//rd
//	public Freview readdetail(Integer frid) {
//		Optionnal<Freview> ob = freviewRepository.findById(fid);
//		return ob.get();
//	}
//	
	
	
	
	// Update
	public void update(Freview freview) {
		freviewRepository.save(freview);
	}

    // Delete
    @Transactional
    public void delete(Integer frid) {
        Freview existingFreview = freviewRepository.findById(frid)
            .orElseThrow(() -> new IllegalArgumentException("삭제할 리뷰를 찾을 수 없습니다."));
        
        freviewRepository.delete(existingFreview);
    }
    //추천

    public void vote(Freview freview, Member member) {
    	freview.getVoter().add(member);
        this.freviewRepository.save(freview);
    }

    
    //비추천
 
    public void devote(Freview freview, Member member) {
        freview.getDevoter().add(member);
        this.freviewRepository.save(freview);
    }

    public Freview getFreview(Integer frid) {
        Optional<Freview> freview = this.freviewRepository.findById(frid);
        return freview.orElseThrow(() -> new RuntimeException("Freview not found"));
    }

	public void vote(Freview freview, Class<? extends Object> member) {
		// TODO Auto-generated method stub
		
	}

	public void devote(Freview freview, Class<? extends Object> member) {
		// TODO Auto-generated method stub
		
	}
}