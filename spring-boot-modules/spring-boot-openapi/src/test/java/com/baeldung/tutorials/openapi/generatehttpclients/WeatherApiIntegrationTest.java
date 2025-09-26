package com.baeldung.tutorials.openapi.generatehttpclients;

import com.baeldung.tutorials.openapi.generatehttpclients.api.WeatherApi;
import com.baeldung.tutorials.openapi.generatehttpclients.api.model.CurrentWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WeatherApi weatherApi;

    @Test
    void getCurrentWeather_ShouldReturnCurrentWeather() {
        CurrentWeather currentWeather = weatherApi.getCurrentWeather();

        assertNotNull(currentWeather);
        assertNotNull(currentWeather.getTemperature());
        assertNotNull(currentWeather.getConditions());
    }
}
