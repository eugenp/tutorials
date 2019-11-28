package com.baeldung.architecture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.architecture.model.Weather;
import com.baeldung.architecture.repository.WeatherRepository;
import com.baeldung.architecture.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Weather getTodaysForecast() {
        return weatherRepository.getTodaysForecast();
    }

   

}
