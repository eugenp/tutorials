package com.baeldung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Car {
    private static Logger log = LoggerFactory.getLogger(Car.class);
    private Engine engine;
    private SpeedLimiter speedLimiter;

    public Car(Engine engine) {
        this.engine = engine;
        log.info("Engine initialized");
    }

    public void setSpeedLimiter(SpeedLimiter speedLimiter) {
        this.speedLimiter = speedLimiter;
        log.info("SpeedLimiter initialized");
    }
}
