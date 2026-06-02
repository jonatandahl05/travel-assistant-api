package com.jonatan.travel_assistant_api.service.external;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthDemoService {

    private final WebClient webClient;
    

    // demo method to show how to use basic auth with WebClient
    public String testBearerAuth() {
        return webClient.get()
            .uri("https://httpbin.org/bearer")
            .header("Authorization", "Bearer demo-token")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
    
}
