package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.external.service.AccuweatherService;
import com.baeldung.external.service.YahooTempratureService;

public class ConstructorInjectionWeatherService implements WeatherService {

    private YahooTempratureService yahoo;

    private AccuweatherService accuweather;

    @Autowired
    public ConstructorInjectionWeatherService(YahooTempratureService yahoo, AccuweatherService accuweather) {
        this.yahoo = yahoo;
        this.accuweather = accuweather;
    }

    // Other methods

    @Override
    public int getTemprature(String location) {
        System.out.println("Setter Injection get temprature");
        return yahoo.getTempratureInCelcius(location);
    }

    @Override
    public int getHumidity(String location) {
        System.out.println("Setter Injection get humidity");
        return accuweather.getHumidityPercentage(location);
    }

    @Override
    public boolean willItRainToday(String location) {
        System.out.println("Setter Injection will it rain");
        return accuweather.getChancesOfRain(location) > 0.5;
    }
}
