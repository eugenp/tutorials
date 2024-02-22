package com.baeldung.tutorials.openapi.quotes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class BrokerService {

    private Map<String,BigDecimal> securities = new HashMap<>();

    public BrokerService() {
        Random rnd = new Random();
        securities.put("GOOG", BigDecimal.valueOf(rnd.nextDouble() * 1000.00).setScale(4, RoundingMode.DOWN));
        securities.put("AA", BigDecimal.valueOf(rnd.nextDouble() * 1000.00).setScale(4, RoundingMode.DOWN));
        securities.put("BAEL", BigDecimal.valueOf(rnd.nextDouble() * 1000.00).setScale(4, RoundingMode.DOWN));
    }


    public Optional<BigDecimal> getSecurityPrice(@NonNull String symbol) {
        return Optional.ofNullable(securities.get(symbol));
    }
}
