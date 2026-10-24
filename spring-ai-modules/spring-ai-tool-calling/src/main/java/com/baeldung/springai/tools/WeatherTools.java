package com.baeldung.springai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.baeldung.springai.service.WeatherService;

@Component
public class WeatherTools {

    private final WeatherService weatherService;

    public WeatherTools(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Tool(description = "Get the current weather in a city")
    public WeatherForecast getCurrentWeather(
        @ToolParam(description = "City name, for example Amsterdam") String city) {
        return weatherService.getCurrentWeather(city);
    }
}
