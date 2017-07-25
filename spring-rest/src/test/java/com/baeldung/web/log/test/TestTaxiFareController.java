package com.baeldung.web.log.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.baeldung.web.log.data.TaxiRide;

public class TestTaxiFareController {

    private static final String URL = "http://localhost:" + 8082 + "/spring-rest/taxifare/";

    @Test
    public void givenRequest_whenFetchTaxiFareRateCard_thanOK() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL + "get/", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenTaxiRide_whenCalculatedFare_thanStatus200() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        TaxiRide taxiRide = new TaxiRide(true, 10l);
        String fare = testRestTemplate.postForObject(URL + "calculate/", taxiRide, String.class);

        assertThat(fare, equalTo("200"));
    }
}
