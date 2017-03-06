package com.baeldung.autowire.example;

import org.springframework.stereotype.Component;

@Component
public class LatLongLookup {

    public String countryForCoordinates(Double lat, Double lng) {
        return "UK";
    }
}
