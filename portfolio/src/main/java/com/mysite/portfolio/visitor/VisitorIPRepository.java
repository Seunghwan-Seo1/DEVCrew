package com.mysite.portfolio.visitor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorIPRepository extends JpaRepository<VisitorIP, Integer> {
    // IP 주소로 VisitorIP 엔티티 찾기
    Optional<VisitorIP> findByIpAddress(String ipAddress);
}
