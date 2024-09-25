package com.mysite.portfolio.festival;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.portfolio.S3Service;
import com.mysite.portfolio.member.Member;
import com.mysite.portfolio.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FestivalService {
    
    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private S3Service s3Service; // S3Service 주입

    @Autowired
	private MemberService memberService;
    
    // create
    public void create(Festival festival, List<MultipartFile> files, Member author) throws IOException {
        festival.setFdate(LocalDateTime.now());
        festival.setAuthor(author);

        // 파일 리스트 크기와 파일 상태 확인
        System.out.println("Number of files uploaded: " + files.size());

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            System.out.println("File " + i + ": " + file.getOriginalFilename() + " (isEmpty: " + file.isEmpty() + ")");
        }

        // 파일 이름을 저장할 배열, 최대 5개
        String[] fileNames = new String[5];

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

        // 파일 이름을 Festival 객체에 설정 (null 검증 추가)
        if (fileNames[0] != null) festival.setFimg(fileNames[0]);
        if (fileNames[1] != null) festival.setFimg2(fileNames[1]);
        if (fileNames[2] != null) festival.setFimg3(fileNames[2]);
        if (fileNames[3] != null) festival.setFimg4(fileNames[3]);
        if (fileNames[4] != null) festival.setFimg5(fileNames[4]);

        // 데이터 저장
        try {
            this.festivalRepository.save(festival);
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스 출력
            throw new RuntimeException("Failed to save festival data.");
        }
    }







    // readlist
    public List<Festival> filterFestivals(String search, String region, String category) {
        List<Festival> festivals = festivalRepository.findAll();

        return festivals.stream()
            .filter(festival -> (search == null || search.isEmpty() || festival.getFname().toLowerCase().contains(search.toLowerCase()) || festival.getFlocation().toLowerCase().contains(search.toLowerCase())) &&
                                (region.equals("전체") || festival.getFlocation().contains(region)) &&
                                (category.equals("전체") || festival.getFcategory().equalsIgnoreCase(category)))
            .sorted((f1, f2) -> Integer.compare(f2.getVoteScore(), f1.getVoteScore())) // 내림차순 정렬
            .collect(Collectors.toList());
    }

    // readdetail
    public Festival readdetail(Integer fid) {
        Optional<Festival> ob = festivalRepository.findById(fid); 
        return ob.get();
    }

    // update
    public void update(Festival festival, List<MultipartFile> files) throws IOException {
        // 기존 축제 정보를 가져와서 기존 이미지 유지
        Festival existingFestival = festivalRepository.findById(festival.getFid())
                .orElseThrow(() -> new RuntimeException("Festival not found"));

        // 파일 이름을 저장할 리스트
        String[] fileNames = new String[5];

        for (int i = 0; i < files.size() && i < 5; i++) {
            MultipartFile file = files.get(i);
            if (!file.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                s3Service.uploadFile(file, fileName);
                fileNames[i] = fileName; // 각 이미지 이름 저장
            }
        }

        // 각 이미지 필드에 저장, 기존 이미지 유지
        if (fileNames[0] != null) festival.setFimg(fileNames[0]);
        else festival.setFimg(existingFestival.getFimg());

        if (fileNames[1] != null) festival.setFimg2(fileNames[1]);
        else festival.setFimg2(existingFestival.getFimg2());

        if (fileNames[2] != null) festival.setFimg3(fileNames[2]);
        else festival.setFimg3(existingFestival.getFimg3());

        if (fileNames[3] != null) festival.setFimg4(fileNames[3]);
        else festival.setFimg4(existingFestival.getFimg4());

        if (fileNames[4] != null) festival.setFimg5(fileNames[4]);
        else festival.setFimg5(existingFestival.getFimg5());

        // 데이터베이스에 저장
        try {
            festivalRepository.save(festival);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            throw new RuntimeException("Failed to update festival data."); // 사용자 정의 예외 메시지
        }
    }

    // delete
    public void delete(Integer frid) {
        festivalRepository.deleteById(frid);
    }

    //추천

    public void vote(Festival festival, Member member) {
        festival.getVoter().add(member);
        this.festivalRepository.save(festival);
    }

    
    //비추천
    public void devote(Festival festival, Member member) {
        festival.getDevoter().add(member);
        this.festivalRepository.save(festival);
    }
    
    public Festival getFestival(Integer fid) {
		Optional<Festival> festival  = this.festivalRepository.findById(fid);
		return festival.get();
	}
}

