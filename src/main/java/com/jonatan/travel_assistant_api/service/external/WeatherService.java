package com.jonatan.travel_assistant_api.service.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jonatan.travel_assistant_api.dto.WeatherApiResponse;
import com.jonatan.travel_assistant_api.dto.WeatherDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WebClient webClient;


    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.base-url}")
    private String baseUrl;

    

    public WeatherDto getWeather(String city) {

        try {

            WeatherApiResponse response = webClient.get()
                    .uri(baseUrl + "/current.json?key=" + apiKey + "&q=" + city)
                    .retrieve()
                    .bodyToMono(WeatherApiResponse.class)
                    .block();

            String condition = response.current().condition().text();

            return new WeatherDto(condition, getCategory(condition));

        } catch (Exception e) {

            System.err.println("Weather API failed, using fallback weather: " + e.getMessage());
            return new WeatherDto("Sunny", "Outdoor");

        }

    }

    private String getCategory(String condition) {

        String lower = condition.toLowerCase();

        if (lower.contains("rain") || lower.contains("snow") || lower.contains("storm")) {
            return "Indoor";
        } else if (lower.contains("sunny") || lower.contains("clear")) {
            return "Outdoor";
        } else {
            return "Mixed";
        }

    }
}
