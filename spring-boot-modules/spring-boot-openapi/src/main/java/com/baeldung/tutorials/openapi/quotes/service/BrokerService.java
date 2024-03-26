package com.baeldung.tutorials.openapi.quotes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class BrokerService {

    private final Logger log = LoggerFactory.getLogger(BrokerService.class);

    private final Random rnd = new Random();


    public BrokerService() {
    }


    public BigDecimal getSecurityPrice(@NonNull String symbol) {
        log.info("getSecurityPrice: {}", symbol);
        // Just a mock value
        return BigDecimal.valueOf(100.0 + rnd.nextDouble()*100.0);
    }
}
