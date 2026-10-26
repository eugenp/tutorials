package com.baeldung.springai.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baeldung.springai.tools.WeatherForecast;

@Service
public class WeatherService {

    private static final Map<String, WeatherForecast> FORECASTS = Map.of(
        "amsterdam", new WeatherForecast("Amsterdam", "sunny", 18),
        "copenhagen", new WeatherForecast("Copenhagen", "rainy", 11),
        "london", new WeatherForecast("London", "cloudy", 14));

    public WeatherForecast getCurrentWeather(String city) {
        return FORECASTS.getOrDefault(city.toLowerCase(Locale.ROOT), new WeatherForecast(city, "cloudy", 15));
    }
}
