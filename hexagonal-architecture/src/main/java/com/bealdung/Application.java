package com.bealdung;

import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.WeatherRepository;

public class Application {
    private WeatherRepository repository;

    public Application(WeatherRepository repository) {
        this.repository = repository;
    }

    public void saveWeather(WeatherInfo weatherInfo) {
        repository.save(weatherInfo);
    }
}
