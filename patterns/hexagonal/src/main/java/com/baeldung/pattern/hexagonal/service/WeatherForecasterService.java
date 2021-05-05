package com.baeldung.pattern.hexagonal.service;

import com.baeldung.pattern.hexagonal.repository.WeatherRepository;

public class WeatherForecasterService {
    private final WeatherRepository repository;

    public WeatherForecasterService(WeatherRepository repository) {
        this.repository = repository;
    }

    public double forecast(String location) {
        String normalizedLocation = getNormalizedLocation(location);
        return repository.getTemperature(normalizedLocation);
    }

    private String getNormalizedLocation(String location) {
        return location == null ? "" : location.toLowerCase();
    }
}
