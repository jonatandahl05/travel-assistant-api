package com.jonatan.travel_assistant_api.service.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jonatan.travel_assistant_api.dto.ActivityDto;
import com.jonatan.travel_assistant_api.dto.CoordinatesDto;
import com.jonatan.travel_assistant_api.dto.GeoapifyGeocodeResponse;
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

        CoordinatesDto coordinates = getCoordinates(city);
        

        // bygger URL:en med hjälp av UriComponentsBuilder för att göra det mer läsbart och hantera query-parametrar på ett snyggt sätt
        String url = baseUrl +
              "/places?categories=" + geoapifyCategory +
              "&filter=circle:" + coordinates.lon() + "," + coordinates.lat() + ",5000" +
              "&bias=proximity:" + coordinates.lon() + "," + coordinates.lat() +
              "&limit=5" +
              "&apiKey=" + apiKey;

        System.out.println(url);

        try {

        GeoapifyResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(GeoapifyResponse.class)
                .block();
        
        // om svaret är null eller inte innehåller några features, returnera en fallback-lista med aktiviteter
        if (response == null || response.features() == null || response.features().isEmpty()) {
            return getFallbackActivities(weatherCategory);
    
        }

        List<ActivityDto> activities = response.features()
            .stream()
            .map(feature -> new ActivityDto(
                feature.properties().name() != null ? feature.properties().name() : feature.properties().formatted(), // använder formatted som namn om name är null
                geoapifyCategory,
                feature.properties().formatted() // använder formatted som platsbeskrivning
            ))
            .toList();

        if (activities.isEmpty()) {
            return getFallbackActivities(weatherCategory);
        }

        return activities;

        } catch (Exception e) {
            // Logga felet och returnera fallback-aktiviteter
            System.err.println("Error fetching activities from Geoapify: " + e.getMessage());
            return getFallbackActivities(weatherCategory);
        }
        
    }

    private String mapWeatherToGeoapifyCategory(String weatherCategory) {
        return switch (weatherCategory) {
            case "Outdoor" -> "leisure.park";
            case "Indoor" -> "entertainment.museum";
            default -> "tourism.attraction";
        };
    }

  private CoordinatesDto getCoordinates(String city) {
    try {
        String url = "https://api.geoapify.com/v1/geocode/search" +
                "?text=" + city +
                "&limit=1" +
                "&apiKey=" + apiKey;

        GeoapifyGeocodeResponse response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(GeoapifyGeocodeResponse.class)
                .block();

        if (response == null || response.features() == null || response.features().isEmpty()) {
            return new CoordinatesDto(13.0038, 55.6050);
        }

        GeoapifyGeocodeResponse.Properties properties =
                response.features().get(0).properties();

        return new CoordinatesDto(properties.lon(), properties.lat());

    } catch (Exception e) {
        System.err.println("Error fetching coordinates from Geoapify: " + e.getMessage());
        return new CoordinatesDto(13.0038, 55.6050);
    }
}

    // Om API-anropet misslyckas eller inte returnerar några aktiviteter, returnera en fördefinierad lista med aktiviteter baserat på väderkategorin
    private List<ActivityDto> getFallbackActivities(String weatherCategory) {
        return List.of(
            new ActivityDto("Malmö Museum", weatherCategory, "Malmö"),
            new ActivityDto("Kungsparken", weatherCategory, "Malmö")
        );
    }


    
}
