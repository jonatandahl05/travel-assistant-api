package com.jonatan.travel_assistant_api.service.recommendation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jonatan.travel_assistant_api.dto.ActivityDto;
import com.jonatan.travel_assistant_api.dto.RecommendationResponse;
import com.jonatan.travel_assistant_api.dto.WeatherDto;
import com.jonatan.travel_assistant_api.service.external.ActivityService;
import com.jonatan.travel_assistant_api.service.external.WeatherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final WeatherService weatherService;
    private final ActivityService activityService;

    public RecommendationResponse getRecommendations(String city) {
        WeatherDto weather = weatherService.getWeather(city);

        String recommendedCategory = weather.category();

        List<ActivityDto> activities = activityService.getActivities(city, recommendedCategory);
    

        return new RecommendationResponse(city, weather, recommendedCategory, activities);
    }
    
}
