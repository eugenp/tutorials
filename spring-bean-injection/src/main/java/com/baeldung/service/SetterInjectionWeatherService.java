package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.external.service.AccuweatherService;
import com.baeldung.external.service.YahooTempratureService;

public class SetterInjectionWeatherService implements WeatherService {

    private YahooTempratureService yahoo;
    
    @Autowired
    public void setYahoo(YahooTempratureService yahoo) {
        this.yahoo = yahoo;
    }
    
    private AccuweatherService accuweather;
    
    public void Accuweather(AccuweatherService accuweather) {
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
