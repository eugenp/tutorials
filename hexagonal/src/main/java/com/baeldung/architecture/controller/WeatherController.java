package com.baeldung.architecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.model.Weather;
import com.baeldung.architecture.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/forecast")
    public Weather getTodaysForecast() {
        return weatherService.getTodaysForecast();
    }

}
