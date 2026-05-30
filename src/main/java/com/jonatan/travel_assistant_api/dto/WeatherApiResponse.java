package com.jonatan.travel_assistant_api.dto;




public record WeatherApiResponse(

        Current current

) {

    public record Current(

            Condition condition

    ) {

    }

    public record Condition(

            String text

    ) {

    }

}