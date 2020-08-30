package com.baeldung.spring.cloud.ribbon.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    private int nrOfCalls = 0;

    @Value("${successful.call.divisor}")
    private int divisor;

    @GetMapping("/")
    public String health() {
        return "I am Ok";
    }

    @GetMapping("/weather")
    public ResponseEntity<String> weather() {
        LOGGER.info("Providing today's weather information");
        if (isServiceUnavailable()) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        LOGGER.info("Today's a sunny day");
        return new ResponseEntity<>("Today's a sunny day", HttpStatus.OK);
    }

    private boolean isServiceUnavailable() {
        return ++nrOfCalls % divisor != 0;
    }
}
