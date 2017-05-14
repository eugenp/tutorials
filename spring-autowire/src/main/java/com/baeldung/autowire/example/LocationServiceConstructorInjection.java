package com.baeldung.autowire.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationServiceConstructorInjection {
    private LatLongLookup latLongLookup;

    @Autowired
    public LocationServiceConstructorInjection(LatLongLookup latLongLookup) {
        this.latLongLookup = latLongLookup;
    }

    public String countryForCoordinates(Double lat, Double lng) {
        return latLongLookup.countryForCoordinates(lat, lng);
    }
}
