package com.baeldung.web.controller;

import com.baeldung.web.log.app.Application;
import com.baeldung.web.log.data.TaxiRide;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaxiFareControllerIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @Test
    public void givenRequest_whenFetchTaxiFareRateCard_thanOK() {
        String URL = "http://localhost:" + port + "/spring-rest";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        TaxiRide taxiRide = new TaxiRide(true, 10l);
        String fare = testRestTemplate.postForObject(
          URL + "/taxifare/calculate/", 
          taxiRide, String.class);
      
        assertThat(fare, equalTo("200"));
    }

}