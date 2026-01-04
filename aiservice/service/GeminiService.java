package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Service
@Slf4j
public class GeminiService {
	
	private final WebClient webClient;
	//@Value("${gemini.api.url}")
	private String geminiApiUrl= "/v1beta/models/gemini-2.0-flash:generateContent";
	//@Value("${gemini.api.key}")
	private String geminiApiKey = "";

	
	public GeminiService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.baseUrl("https://generativelanguage.googleapis.com")
	            .build();
	}
	
	public String getAnswer(String question) {
		Map<String, Object> requestBody = Map.of(
			    "contents", new Object[] {
			        Map.of("parts", new Object[] {
			                Map.of("text", question)
			            })
			    }
			);
		 
		try {
		 String response = webClient.post()
				 .uri(uriBuilder -> uriBuilder
			                .path(geminiApiUrl)
			                .queryParam("key", geminiApiKey)
			                .build()
			            )
				 .header("Content-Type", "application/json")
				 .bodyValue(requestBody)
				 .retrieve()
				 .bodyToMono(String.class)
				 .block();
		 
		 return response;
		} catch(WebClientResponseException.TooManyRequests e) {
			log.error("Gemini rate limit exceeded");
	        return "AI service temporarily unavailable. Please retry later.";
		}
	}
}


