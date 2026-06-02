package com.jonatan.travel_assistant_api.dto;

import java.util.List;

public record GeoapifyResponse (
    List<Feature> features
) {
    public record Feature (
        Properties properties
    ) {
        public record Properties (
            String name,
            String formatted,
            List<String> categories
        ) {
        }
    }
    
}
