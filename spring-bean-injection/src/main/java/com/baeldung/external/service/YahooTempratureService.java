package com.baeldung.external.service;

import org.springframework.stereotype.Service;

@Service
public class YahooTempratureService {
    
    public int getTempratureInCelcius(String location) {
        System.out.println("Yahoo get temprature");
        return 23;
    }
}
