package com.jonatan.travel_assistant_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonatan.travel_assistant_api.dto.RecommendationResponse;
import com.jonatan.travel_assistant_api.service.recommendation.RecommendationService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    
    @GetMapping
    public RecommendationResponse getRecommendations(@RequestParam String city) {
        return recommendationService.getRecommendations(city);
    }

    
    
}
