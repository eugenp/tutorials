package com.baeldung.service;

import org.springframework.stereotype.Service;

import com.baeldung.external.service.AccuweatherService;
import com.baeldung.external.service.YahooTempratureService;

@Service
public class FieldInjectionWeatherService implements WeatherService {
    
    private YahooTempratureService yahoo;
    
    private AccuweatherService accuweather;
    
    @Override
    public int getTemprature(String location) {
        System.out.println("Field Injection get temprature");
        return yahoo.getTempratureInCelcius(location);
    }

    @Override
    public int getHumidity(String location) {
        System.out.println("Field Injection get humidity");
        return yahoo.getTempratureInCelcius(location);
    }

    @Override
    public boolean willItRainToday(String location) {
        System.out.println("Field Injection will it rain");
        return accuweather.getChancesOfRain(location) > 0.5;
    }
}
