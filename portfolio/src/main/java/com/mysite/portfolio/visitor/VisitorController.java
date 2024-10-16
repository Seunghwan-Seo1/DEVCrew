/*생산자 : 서승환
생산일 : 2024-09-19*/

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
        String clientIp = getClientIp(request); // 클라이언트의 실제 IP 주소 가져오기
        visitorService.incrementVisitorCount(clientIp);
        return "Visitor count incremented if not duplicate!";
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }


    @GetMapping("/count")
    public Integer getVisitorCount() {
        return visitorService.getVisitorCount();
    }
    
    
}
