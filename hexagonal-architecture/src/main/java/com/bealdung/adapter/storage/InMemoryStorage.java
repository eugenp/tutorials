package com.bealdung.adapter.storage;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.repository.WeatherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryStorage implements WeatherRepository {

    private List<WeatherInfo> weatherInfos;

    public InMemoryStorage() {
        this.weatherInfos = new ArrayList<>();
    }

    public WeatherInfo save(WeatherInfo weatherInfo) {
        weatherInfo.setId(UUID.randomUUID());
        weatherInfos.add(weatherInfo);
        return weatherInfo;
    }

    public List<WeatherInfo> getWeatherInfo(SourceType source) {
        return weatherInfos.stream()
                .filter(weatherInfo -> weatherInfo.getSource().equals(source))
                .collect(Collectors.toList());
    }
}
