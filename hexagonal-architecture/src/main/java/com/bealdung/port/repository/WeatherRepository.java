package com.bealdung.port.repository;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;

import java.util.List;

public interface WeatherRepository {
    WeatherInfo save(WeatherInfo weatherInfo);

    List<WeatherInfo> getWeatherInfo(SourceType source);
}
