package com.baeldung.reactive.evaluation;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ApplicationHealthService implements HealthService {

    @Override
    public String isAlive() {
        return new Random().nextBoolean() ? "alive" : "not-alive";
    }
}