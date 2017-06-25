package com.baeldung.spring.drools.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.spring.drools.model.Fare;
import com.baeldung.spring.drools.model.TaxiRide;

public class TaxiFareCalculatorService {

    @Autowired
    private KieSession kieSession;

    public Long calculateFare(TaxiRide taxiRide, Fare rideFare) {
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("!! RIDE FARE !! " + rideFare.getTotalFare());
        return rideFare.getTotalFare();
    }
}
