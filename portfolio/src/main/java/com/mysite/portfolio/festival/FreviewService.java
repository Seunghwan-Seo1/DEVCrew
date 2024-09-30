package com.mysite.portfolio.festival;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.admin.NotificationService;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreviewService {

	@Autowired
	private final FreviewRepository freviewRepository;

	private final FestivalRepository festivalRepository;
	private final NotificationService notificationService;
	
	
	//c  
		public void create(Freview freview, Integer fid, Member author){

			Optional<Festival> of = festivalRepository.findById(fid);
			freview.setFestival(of.get());		
			freview.setFrcreateDate(LocalDateTime.now());
			freview.setAuthor(author);
			
			
			this.freviewRepository.save(freview);
			
			// 게시글 작성 후 작성자에게만 알림 전송
	        String message = "새로운 댓글이 달렸습니다.";
	        notificationService.sendNotificationToUserByUsername(author.getUsername(), message);

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

	
	
	//update
	public void update(Freview freview, Member author) {
	    // 기존 Freview 정보 가져오기
	    Optional<Freview> _freview = freviewRepository.findById(freview.getFrid());

	    if (_freview.isPresent()) {
	        Freview freviewData = _freview.get();

	        // 기존 voter와 devoter 유지
	        Set<Member> existingVoters = freviewData.getVoter();
	        Set<Member> existingDevoters = freviewData.getDevoter();

	        // 투표 정보는 유지하고, 필요한 필드만 업데이트
	        freviewData.setFrcontent(freview.getFrcontent());
	        freviewData.setAuthor(author);

	        // 기존 voter와 devoter를 유지
	        freviewData.setVoter(existingVoters);
	        freviewData.setDevoter(existingDevoters);

	        // 업데이트된 Freview 저장
	        this.freviewRepository.save(freviewData);
	    } else {
	        throw new RuntimeException("Freview not found with id: " + freview.getFrid());
	    }
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