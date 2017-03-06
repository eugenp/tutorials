package com.baeldung.autowire.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationServiceSetterInjection {
    private LatLongLookup latLongLookup;

    public String countryForCoordinates(Double lat, Double lng) {
        return latLongLookup.countryForCoordinates(lat, lng);
    }

    @Autowired
    public void setLatLongLookup(LatLongLookup latLongLookup) {
        this.latLongLookup = latLongLookup;
    }
}
