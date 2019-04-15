package com.bealdung.adapter;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.WeatherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryStorage implements WeatherRepository {

    private List<WeatherInfo> weatherInfos;

    public InMemoryStorage() {
        this.weatherInfos = new ArrayList<>();
    }

    public void save(WeatherInfo weatherInfo) {
        weatherInfos.add(weatherInfo);
    }

    public List<WeatherInfo> getWeatherInfo(SourceType source) {
        return weatherInfos.stream()
                .filter(weatherInfo -> weatherInfo.getSource().equals(source))
                .collect(Collectors.toList());
    }
}
