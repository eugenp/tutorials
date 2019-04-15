package com.bealdung.port;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;

import java.util.List;

public interface WeatherRepository {
    void save(WeatherInfo weatherInfo);

    List<WeatherInfo> getWeatherInfo(SourceType source);
}
