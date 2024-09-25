package com.mysite.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@RestController
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    // 사용자가 질문을 POST 요청으로 보냄
    @PostMapping("/ask")
    public Mono<String> askQuestion(@RequestBody String question) {
        // GPT-3.5 Turbo를 사용한 응답 처리 및 에러 처리
        return openAIService.getChatCompletion(question)
                .onErrorResume(WebClientResponseException.class, e -> {
                    // WebClient에서 발생하는 HTTP 에러 처리
                    if (e.getStatusCode().is4xxClientError()) {
                        return Mono.just("Client Error: " + e.getMessage());
                    } else if (e.getStatusCode().is5xxServerError()) {
                        return Mono.just("Server Error: " + e.getMessage());
                    } else {
                        return Mono.just("Unexpected Error: " + e.getMessage());
                    }
                })
                .onErrorResume(Exception.class, e -> {
                    // 그 외 일반적인 에러 처리
                    return Mono.just("Error: " + e.getMessage());
                });
    }
}



