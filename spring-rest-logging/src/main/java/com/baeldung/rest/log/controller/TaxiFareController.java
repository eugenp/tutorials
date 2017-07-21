package com.baeldung.rest.log.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.rest.log.data.RateCard;
import com.baeldung.rest.log.data.TaxiRide;
import com.baeldung.rest.log.service.TaxiFareCalculatorService;

@Controller
public class TaxiFareController {

    @Autowired
    private TaxiFareCalculatorService taxiFareCalculatorService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxiFareController.class);
    
    @RequestMapping(method = RequestMethod.GET, value = "/taxifare/get/")
    @ResponseBody
    public RateCard getTaxiFare() {
        LOGGER.debug("getTaxiFare() - START");
        return new RateCard();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/taxifare/calculate/")
    @ResponseBody
    public String calculateTaxiFare(@RequestBody @Valid TaxiRide taxiRide) {
        LOGGER.debug("calculateTaxiFare() - START");
        String totalFare = taxiFareCalculatorService.calculateFare(taxiRide);
        LOGGER.debug("calculateTaxiFare() - Total Fare : {}",totalFare);
        LOGGER.debug("calculateTaxiFare() - END");
        return totalFare;
    }

}
