package com.mysite.portfolio.visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/increment")
    public String incrementVisitor(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr(); // 클라이언트의 IP 주소 가져오기
        visitorService.incrementVisitorCount(clientIp);
        return "Visitor count incremented if not duplicate!";
    }

    @GetMapping("/count")
    public Integer getVisitorCount() {
        return visitorService.getVisitorCount();
    }
}
