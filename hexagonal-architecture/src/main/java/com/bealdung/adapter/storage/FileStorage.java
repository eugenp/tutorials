package com.bealdung.adapter.storage;

import com.bealdung.dto.SourceType;
import com.bealdung.dto.WeatherInfo;
import com.bealdung.port.repository.WeatherRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class FileStorage implements WeatherRepository {

    public WeatherInfo save(WeatherInfo weatherInfo) {
        // code to save in file
        throw new NotImplementedException();
    }

    public List<WeatherInfo> getWeatherInfo(SourceType source) {
        throw new NotImplementedException();
    }
}
