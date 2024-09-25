package com.mysite.portfolio;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

@Service
public class OpenAIService {

    private final WebClient webClient;

    // 생성자를 통해 API 키를 주입받고 WebClient를 설정합니다.
    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    // 사용자 질문에 대해 GPT-4 모델을 호출하는 메서드
    public Mono<String> getChatCompletion(String question) {
        // 요청 바디 생성 (구조적으로 관리)
        JSONObject requestBody = createRequestBody(question);

        // WebClient를 통해 비동기적으로 API 호출
        return webClient.post()
                .bodyValue(requestBody.toString())  // JSON 바디를 문자열로 변환하여 전송
                .retrieve()
                .bodyToMono(String.class)  // 응답을 Mono로 받음
                .map(responseBody -> parseResponseBody(responseBody))  // 응답 파싱
                .onErrorResume(WebClientResponseException.class, e -> handleError(e));
    }

    // 요청 바디를 구성하는 메서드 (GPT-4 모델 사용)
    private JSONObject createRequestBody(String question) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");  // GPT-4 모델 사용

        // messages 배열을 구성하여 사용자 메시지 추가
        JSONArray messages = new JSONArray();
        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");  // 역할 설정
        messageObject.put("content", question);  // 사용자의 질문

        messages.put(messageObject);  // 메시지를 배열에 추가
        requestBody.put("messages", messages);  // messages 필드를 요청 바디에 추가

        return requestBody;  // 구성된 요청 바디 반환
    }

    // 응답 본문을 파싱하는 메서드
    private String parseResponseBody(String responseBody) {
        // JSON 파싱하여 응답 메시지 추출
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");

        // 응답이 유효한지 확인한 후 첫 번째 응답의 내용을 추출
        if (choices.length() > 0) {
            // choices 배열의 첫 번째 항목에서 "message"의 "content" 필드를 가져옴
            String content = choices.getJSONObject(0).getJSONObject("message").getString("content").trim();
            return content;  // 메시지 내용만 반환
        }

        return "유효한 응답을 찾을 수 없습니다.";  // 유효한 응답이 없는 경우
    }


    // API 호출 중 에러가 발생했을 때 처리하는 메서드
    private Mono<String> handleError(WebClientResponseException e) {
        // 에러 코드에 따른 처리
        if (e.getStatusCode().is4xxClientError()) {
            return Mono.just("Client Error: " + e.getMessage());
        } else if (e.getStatusCode().is5xxServerError()) {
            return Mono.just("Server Error: " + e.getMessage());
        } else {
            return Mono.just("Unexpected Error: " + e.getMessage());
        }
    }
    
    // 대화 히스토리를 포함한 GPT-4 모델 호출 메서드
    public Mono<String> getChatCompletion(List<Map<String, String>> conversationHistory) {
        // 요청 바디 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4");  // GPT-4 모델 사용

        // 대화 히스토리를 messages 배열로 전달
        JSONArray messages = new JSONArray();
        for (Map<String, String> message : conversationHistory) {
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", message.get("role"));
            messageObject.put("content", message.get("content"));
            messages.put(messageObject);
        }

        requestBody.put("messages", messages);

        // WebClient를 사용하여 API 호출
        return webClient.post()
                .bodyValue(requestBody.toString())
                .retrieve()
                .bodyToMono(String.class)
                .map(responseBody -> {
                    JSONObject responseJson = new JSONObject(responseBody);
                    JSONArray choices = responseJson.getJSONArray("choices");
                    return choices.getJSONObject(0).getJSONObject("message").getString("content").trim();
                });
    }
}
