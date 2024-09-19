package com.mysite.portfolio.visitor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitorIPRepository visitorIPRepository;

    public synchronized void incrementVisitorCount(String ipAddress) {
        // IP 중복 체크 및 업데이트
        if (isDuplicateVisit(ipAddress)) {
            System.out.println("중복 방문입니다: " + ipAddress);
            return;
        }
        
        
        // 방문자 수 증가 및 방문자 데이터 업데이트
        Visitor visitor = visitorRepository.findById(1).orElse(new Visitor());
        if (visitor.getVcount() == null) {
            visitor.setVcount(0);
        }
        visitor.setVcount(visitor.getVcount() + 1);
        visitor.setVdate(LocalDateTime.now());
        visitor.setVisitDate(LocalDate.now());
        visitorRepository.save(visitor);

        // 방문 IP 정보 저장 또는 업데이트
        VisitorIP visitorIP = visitorIPRepository.findByIpAddress(ipAddress).orElse(new VisitorIP());
        visitorIP.setIpAddress(ipAddress);
        visitorIP.setLastVisit(LocalDateTime.now());
        visitorIPRepository.save(visitorIP);
    }

    // IP 중복 체크 로직
    private boolean isDuplicateVisit(String ipAddress) {
        // IP로 VisitorIP 엔티티를 찾고, 마지막 방문 시간이 1시간 이내인지 체크
        return visitorIPRepository.findByIpAddress(ipAddress)
                .map(visitorIP -> visitorIP.getLastVisit().plusHours(1).isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public Integer getVisitorCount() {
        return visitorRepository.findById(1)
                .map(visitor -> visitor.getVcount() == null ? 0 : visitor.getVcount())
                .orElse(0);
    }
    
    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }


    public Integer getTotalVisitorCount() {
        return visitorRepository.getTotalVisitorCount(); // 총 방문자 수를 조회하는 메소드
    }
    // 전체 방문자 데이터 조회
    public List<Visitor> getAllVisitorData() {
        return visitorRepository.findAllVisitors();
    }

    // 특정 날짜의 방문자 수 조회
    public List<Visitor> getVisitorsByDate(LocalDate date) {
        return visitorRepository.findVisitorsByVisitDate(date);
    }
    
    public List<Integer> getLast7DaysVisitorCounts() {
        List<Integer> dailyCounts = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        // 최근 7일의 방문자 수를 계산
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int count = visitorRepository.countVisitorsByVisitDate(date);
            dailyCounts.add(count);
        }
        
        return dailyCounts;
    }

}



