package com.baeldung.web.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.baeldung.web.log.app.Application;
import com.baeldung.web.log.data.TaxiRide;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class, TaxiFareControllerIntegrationTest.SecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaxiFareControllerIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @Test
    public void givenRequest_whenFetchTaxiFareRateCard_thanOK() {

        System.out.println(port);
        String URL = "http://localhost:" + port + "/spring-rest";
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        TaxiRide taxiRide = new TaxiRide(true, 10l);
        String fare = testRestTemplate.postForObject(
          URL + "/taxifare/calculate/", 
          taxiRide, String.class);
      
        assertThat(fare, equalTo("200"));
    }

    @Configuration
    static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            System.out.println("security being set");
            http
                    .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                    .csrf().disable();
        }
    }
}