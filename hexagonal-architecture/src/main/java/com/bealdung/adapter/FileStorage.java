package com.bealdung.adapter;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.WeatherRepository;

import java.util.Collections;
import java.util.List;

public class FileStorage implements WeatherRepository {

    public void save(WeatherInfo weatherInfo) {
        // code to save in file
    }

    public List<WeatherInfo> getWeatherInfo(SourceType source) {
        return Collections.emptyList();
    }
}
