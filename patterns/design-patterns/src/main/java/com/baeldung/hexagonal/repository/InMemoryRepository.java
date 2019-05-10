package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.WeatherAlert;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements RepositoryPort {

    private List<WeatherAlert> weatherAlerts;

    public InMemoryRepository() {
        this.weatherAlerts = new ArrayList<>();
    }

    @Override
    public WeatherAlert save(WeatherAlert weatherAlert) {
        this.weatherAlerts.add(weatherAlert);
        return weatherAlert;
    }

    @Override
    public List<WeatherAlert> findAll() {
        return this.weatherAlerts;
    }
}
