package com.jonatan.travel_assistant_api.service.recommendation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jonatan.travel_assistant_api.dto.ActivityDto;
import com.jonatan.travel_assistant_api.dto.RecommendationResponse;
import com.jonatan.travel_assistant_api.dto.WeatherDto;
import com.jonatan.travel_assistant_api.service.external.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final WeatherService weatherService;

    public RecommendationResponse getRecommendations(String city) {
        WeatherDto weather = weatherService.getWeather(city);
        List<ActivityDto> activities = List.of(
            new ActivityDto("Kungsparken", "park", "Malmö"),
            new ActivityDto("Ribersborg", "beach", "Malmö")

    );

        return new RecommendationResponse(city, weather, weather.category(), activities);
    }
    
}
