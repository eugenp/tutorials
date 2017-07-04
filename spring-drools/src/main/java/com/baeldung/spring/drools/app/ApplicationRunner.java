package com.baeldung.spring.drools.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.spring.drools.model.TaxiRide;
import com.baeldung.spring.drools.model.Fare;
import com.baeldung.spring.drools.service.TaxiFareCalculatorService;
import com.baeldung.spring.drools.service.TaxiFareConfiguration;

public class ApplicationRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TaxiFareConfiguration.class);
        TaxiFareCalculatorService orderService = context.getBean(TaxiFareCalculatorService.class);

        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(true);
        taxiRide.setDistanceInMile(190L);
        Fare rideFare = new Fare();
        orderService.calculateFare(taxiRide, rideFare);
    }

}
