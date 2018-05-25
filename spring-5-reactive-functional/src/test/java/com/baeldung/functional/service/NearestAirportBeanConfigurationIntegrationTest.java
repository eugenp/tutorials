package com.baeldung.functional.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.functional.Spring5JavaApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5JavaApplication.class)
public class NearestAirportBeanConfigurationIntegrationTest {

    @Autowired
    private GenericApplicationContext webApplicationcontext;

    @Test
    public void testRegisterBeanWithNameAndAutowareCandidate_thenOk() {
        webApplicationcontext.registerBean("nearestAirportService", NearestAirportService.class, () -> new NearestAirportService(), e -> e.setAutowireCandidate(false));
        NearestAirportService nearestAirportService = (NearestAirportService) webApplicationcontext.getBean("nearestAirportService");
        assertEquals("NSCBIA", nearestAirportService.getNearestAirport("Kolkata"));
    }
    
    @Test
    public void testRegisterBean_thenOk() {
        webApplicationcontext.registerBean(NearestAirportService.class, () -> new NearestAirportService());
        NearestAirportService nearestAirportService = (NearestAirportService) webApplicationcontext.getBean("com.baeldung.functional.service.NearestAirportService");
        assertEquals("JFK", nearestAirportService.getNearestAirport("New York"));
    }

    @Test
    public void testRegisterBeanWithName_thenOk() {
        webApplicationcontext.registerBean("mySecondService", NearestAirportService.class, () -> new NearestAirportService());
        NearestAirportService mySecondService = (NearestAirportService) webApplicationcontext.getBean("mySecondService");
        assertEquals("JFK", mySecondService.getNearestAirport("New York"));
    }

}