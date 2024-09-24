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
		public void create(Freview freview, Integer fid, Member author){

			Optional<Festival> of = festivalRepository.findById(fid);
			freview.setFestival(of.get());		
			freview.setFrcreateDate(LocalDateTime.now());
			freview.setAuthor(author);
			
			
			this.freviewRepository.save(freview);

		}
	
	//rl
	public List<Freview> frvlist() {
		return freviewRepository.findAll();
	}
	
	//rd
	public Freview readdetail(Integer frid) {
		Optional<Freview> ob = freviewRepository.findById(frid);
		return ob.get();
	}

	
	
	
	// Update
	public void update(Freview freview) {
		Optional<Freview> _freview = freviewRepository.findById(freview.getFrid());
		
		Freview freviewData = _freview.get();
		freviewData.setFrcontent(freview.getFrcontent());
		this.freviewRepository.save(freviewData);
	}

    // Delete
	public void delete(Integer id) {
        this.freviewRepository.deleteById(id);
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

	
}