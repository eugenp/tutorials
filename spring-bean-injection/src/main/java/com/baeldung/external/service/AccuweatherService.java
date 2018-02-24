package com.baeldung.external.service;

import org.springframework.stereotype.Service;

@Service
public class AccuweatherService {
    
    public int getHumidityPercentage(String location) {
        System.out.println("Accuweather get humidity");
        return 20;
    }
    
    public float getChancesOfRain(String location) {
        System.out.println("Accuweather get chances of rain");
        return 0.49f;
    }
}
