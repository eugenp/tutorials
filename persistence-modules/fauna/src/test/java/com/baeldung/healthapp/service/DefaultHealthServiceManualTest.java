package com.baeldung.healthapp.service;

import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.baeldung.healthapp.FaunaHealthApplication;
import com.baeldung.healthapp.domain.HealthData;

@SpringBootTest(classes = FaunaHealthApplication.class)
class DefaultHealthServiceManualTest {

    @Autowired
    private DefaultHealthService defaultHealthService;

    @MockBean
    private GeoLocationService geoLocationService;

    @Test
    void givenEURegion_whenProcess_thenRequestSentToEURegion() throws MalformedURLException, InterruptedException, ExecutionException {

        HealthData healthData = new HealthData("user-1-eu", //
            37.5f, //
            99f, //
            120, 80, //
            51.50, -0.07, //
            ZonedDateTime.now());

        when(geoLocationService.getRegion(51.50, -0.07)).thenReturn("EU");

        defaultHealthService.process(healthData);
    }

    @Test
    void givenUSRegion_whenProcess_thenRequestSentToUSRegion() throws MalformedURLException, InterruptedException, ExecutionException {

        HealthData healthData = new HealthData("user-1-us", //
            38.0f, //
            100f, //
            115, 85, //
            40.75, -74.30, //
            ZonedDateTime.now());

        when(geoLocationService.getRegion(40.75, -74.30)).thenReturn("US");

        defaultHealthService.process(healthData);
    }
}
