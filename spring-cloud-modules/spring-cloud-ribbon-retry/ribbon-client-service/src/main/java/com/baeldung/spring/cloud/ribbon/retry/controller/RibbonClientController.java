package com.baeldung.spring.cloud.ribbon.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonClientController {

    private static final String WEATHER_SERVICE = "weather-service";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/client/weather")
    public String weather() {
        String result = restTemplate.getForObject("http://" + WEATHER_SERVICE + "/weather", String.class);
        return "Weather Service Response: " + result;
    }
}
