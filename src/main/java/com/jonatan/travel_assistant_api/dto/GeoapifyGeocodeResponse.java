package com.jonatan.travel_assistant_api.dto;

import java.util.List;

public record GeoapifyGeocodeResponse(
        List<Feature> features
) {

    public record Feature(
            Properties properties
    ) {
    }

    public record Properties(
            double lon,
            double lat
    ) {
    }
}