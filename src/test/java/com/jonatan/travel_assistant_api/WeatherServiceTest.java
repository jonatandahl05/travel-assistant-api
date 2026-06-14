package com.jonatan.travel_assistant_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jonatan.travel_assistant_api.dto.WeatherDto;
import com.jonatan.travel_assistant_api.service.external.WeatherService;

@SpringBootTest
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void shouldReturnWeatherDto() {
        WeatherDto weather = weatherService.getWeather("Malmo");

        assertNotNull(weather);
        assertNotNull(weather.condition());
        assertNotNull(weather.category());
    }

    @Test
    void shouldUseFallbackWhenWeatherApiFails() {
        WeatherDto weather = weatherService.getWeather("InvalidCityThatShouldStillNotCrash");

        assertNotNull(weather);
        assertNotNull(weather.condition());
        assertNotNull(weather.category());
    }
}