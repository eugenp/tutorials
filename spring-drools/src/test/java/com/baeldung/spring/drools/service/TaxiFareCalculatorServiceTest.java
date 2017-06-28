package com.baeldung.spring.drools.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.spring.drools.model.TaxiRide;
import com.baeldung.spring.drools.model.Fare;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TaxiFareConfiguration.class)
public class TaxiFareCalculatorServiceTest {

    @Autowired
    TaxiFareCalculatorService taxiFareCalculatorService;

    @Test
    public void testCalculateFareScenario1() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(false);
        taxiRide.setDistanceInMile(9L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(70), totalCharge);
    }
    
    @Test
    public void testCalculateFareScenario2() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(true);
        taxiRide.setDistanceInMile(5L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(100), totalCharge);
    }

    @Test
    public void testCalculateFareScenario3() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(false);
        taxiRide.setDistanceInMile(50L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(170), totalCharge);
    }
    
    @Test
    public void testCalculateFareScenario4() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(true);
        taxiRide.setDistanceInMile(50L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(250), totalCharge);
    }
    
    @Test
    public void testCalculateFareScenario5() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(false);
        taxiRide.setDistanceInMile(100L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(220), totalCharge);
    }
    
    @Test
    public void testCalculateFareScenario6() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setbNightSurcharge(true);
        taxiRide.setDistanceInMile(100L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        Assert.assertNotNull(totalCharge);
        Assert.assertEquals(Long.valueOf(350), totalCharge);
    }
   
}
