package com.baeldung.spring.drools.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.drools.model.Fare;
import com.baeldung.spring.drools.model.TaxiRide;
import com.baeldung.spring.drools.service.TaxiFareCalculatorService;

@RestController
@RequestMapping("/")
public class ApplicationController {

	@Autowired
	TaxiFareCalculatorService taxiFareCalculatorService;

	@GetMapping("taxi")
	public void taxi() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(true);
        taxiRide.setDistanceInMile(190L);
        
        Fare rideFare = new Fare();
        taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
    }

}