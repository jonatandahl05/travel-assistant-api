package com.jonatan.travel_assistant_api.service.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.jonatan.travel_assistant_api.dto.ActivityDto;
import com.jonatan.travel_assistant_api.dto.GeoapifyResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final WebClient webClient;
    
    
    // hämtar från application.properties
    @Value("${geoapify.api.key}")
    private String apiKey;
    
    @Value("${geoapify.api.base-url}")
    private String baseUrl;
    
    // mappar väderkategorier till Geoapify-kategorier
    public List<ActivityDto> getActivities(String city, String weatherCategory) {
        String geoapifyCategory = mapWeatherToGeoapifyCategory(weatherCategory);
        

        // bygger URL:en med hjälp av UriComponentsBuilder för att göra det mer läsbart och hantera query-parametrar på ett snyggt sätt
        String url = UriComponentsBuilder.fromPath(baseUrl + "/places")
            .queryParam("categories", geoapifyCategory)
            .queryParam("filter", "place:city:" + city)
            .queryParam("limit", 5)
            .queryParam("apiKey", apiKey)
            .build()
            .toUriString();
        
        // gör API-anropet och mappar svaret till GeoapifyResponse-klassen    
        GeoapifyResponse response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(GeoapifyResponse.class)
            .block();
        
        // om svaret är null eller inte innehåller några features, returnera en fallback-lista med aktiviteter
        if (response == null || response.features() == null || response.features().isEmpty()) {
            return getFallbackActivities(weatherCategory);
    
        }

        return response.features()
            .stream()
            .map(feature -> new ActivityDto(
                feature.properties().name() !=null ? feature.properties().name() : "Unknown place",
                geoapifyCategory,
                feature.properties().formatted() // använder formatted som platsbeskrivning
            ))
            .toList();
        
    }

    private String mapWeatherToGeoapifyCategory(String weatherCategory) {
        return switch (weatherCategory) {
            case "Outdoor" -> "leisure.park";
            case "Indoor" -> "entertainment.museum";
            default -> "tourism.sights";
        };
    }

    private List<ActivityDto> getFallbackActivities(String weatherCategory) {
        return List.of(
            new ActivityDto("Malmö Museum", weatherCategory, "Malmö"),
            new ActivityDto("Kungsparken", weatherCategory, "Malmö")
        );
    }


    
}
