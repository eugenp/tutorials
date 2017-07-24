package com.baeldung.rest.log.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baeldung.rest.log.data.TaxiRide;

public class TestTaxiFareController {

    private static final String URL = "http://localhost:" + 9090 + "/rest-log/taxifare/";
    
    @Test
    public void givenRequest_thenfetchTaxiFareRateCard() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL + "get/", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
    @Test
    public void givenTaxiRide_thenGetCalculatedFare() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        TaxiRide taxiRide = new TaxiRide(true,10l);
        String fare = testRestTemplate.postForObject(URL + "calculate/", taxiRide,String.class);
        assertThat(fare, equalTo("200"));
    }
   

}
