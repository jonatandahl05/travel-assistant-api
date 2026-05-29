package com.jonatan.travel_assistant_api.service.recommendation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jonatan.travel_assistant_api.dto.ActivityDto;
import com.jonatan.travel_assistant_api.dto.RecommendationResponse;
import com.jonatan.travel_assistant_api.dto.WeatherDto;

@Service
public class RecommendationService {

    public RecommendationResponse getRecommendations(String city) {
        // Mocked data for demonstration purposes
        WeatherDto weather = new WeatherDto("Sunny", "Clear");
        String recommendedCategory = "Outdoor";
        List<ActivityDto> activities = List.of(
            new ActivityDto("Kungsparken", "park", "Malmö"),
            new ActivityDto("Ribersborg", "beach", "Malmö")

    );

        return new RecommendationResponse(city, weather, recommendedCategory, activities);
    }
    
}
