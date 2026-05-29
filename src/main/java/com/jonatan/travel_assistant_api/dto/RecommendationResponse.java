package com.jonatan.travel_assistant_api.dto;

import java.util.List;

public record RecommendationResponse (String city, WeatherDto weather, String recommendedCategory, List<ActivityDto> activities) {
    
}
