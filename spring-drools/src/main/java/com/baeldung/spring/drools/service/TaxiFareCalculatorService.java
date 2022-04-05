package com.baeldung.spring.drools.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spring.drools.model.Fare;
import com.baeldung.spring.drools.model.TaxiRide;

@Service
public class TaxiFareCalculatorService {

    @Autowired
    private KieContainer kContainer;

    public Long calculateFare(TaxiRide taxiRide, Fare rideFare) {
        KieSession kieSession = kContainer.newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("!! RIDE FARE !! " + rideFare.getTotalFare());
        return rideFare.getTotalFare();
    }
}
