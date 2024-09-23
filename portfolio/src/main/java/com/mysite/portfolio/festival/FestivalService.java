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
    public void create(Festival festival, List<MultipartFile> files) throws IOException {
        festival.setFdate(LocalDateTime.now());

        String[] fileNames = new String[5];

        
        for (int i = 0; i < files.size() && i < 5; i++) {
            MultipartFile file = files.get(i+1);
 
            if (!file.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                s3Service.uploadFile(file, fileName);
                fileNames[i] = fileName;
            }
        }

        // 각 이미지 필드에 저장

        // 임시 확인용
        System.out.println(files.get(1));
        System.out.println(files.get(2));
        System.out.println(files.get(3));
        System.out.println(files.get(4));
        System.out.println(files.get(5));
        
        System.out.println("----------");
        
        System.out.println(fileNames[0]);
        System.out.println(fileNames[1]);
        System.out.println(fileNames[2]);
        System.out.println(fileNames[3]);
        System.out.println(fileNames[4]);
        
        

        festival.setFimg(fileNames[0]);
        festival.setFimg2(fileNames[1]);
        festival.setFimg3(fileNames[2]);
        festival.setFimg4(fileNames[3]);
        festival.setFimg5(fileNames[4]);

        try {
            this.festivalRepository.save(festival);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            throw new RuntimeException("Failed to save festival data.");
        }
    }


    // readlist
    public List<Festival> readlist() {
        return festivalRepository.findAll();
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
    public void delete(Integer id) {
        festivalRepository.deleteById(id);
    }
 
    
    public void vote(Festival festival, Member member) {
        festival.getVoter().add(member);
        this.festivalRepository.save(festival);
    }



    
    }

