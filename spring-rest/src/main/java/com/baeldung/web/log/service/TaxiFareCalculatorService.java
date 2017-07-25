package com.baeldung.web.log.service;

import org.springframework.stereotype.Service;

import com.baeldung.web.log.data.TaxiRide;

@Service
public class TaxiFareCalculatorService {

    public String calculateFare(TaxiRide taxiRide) {
        Long fare = 0l;
        if (taxiRide.getIsNightSurcharge()) {
            fare = taxiRide.getDistanceInMile() * 10 + 100;
        } else {
            fare = taxiRide.getDistanceInMile() * 10;
        }
        return String.valueOf(fare);
    }

}
