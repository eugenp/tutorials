package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoolingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoolingController.class);
    private static final Integer DEFAULT_RADIATOR_SPEED = 10;

    private Integer temperatureUpperLimit;
    private Radiator radiator = new Radiator();
    private TemperatureSensor temperatureSensor = new TemperatureSensor();

    public void setTemperatureUpperLimit(Integer temperatureUpperLimit) {
        LOGGER.info("Setting temperature upper limit to {}", temperatureUpperLimit);
        this.temperatureUpperLimit = temperatureUpperLimit;
    }

    public void run() {
        LOGGER.info("Cooling Controller ready!");
        radiator.setSpeed(DEFAULT_RADIATOR_SPEED);
    }

    public void cool(Integer maxAllowedTemp) {
        LOGGER.info("Scheduled cooling with maximum allowed temperature {}", maxAllowedTemp);
        temperatureSensor.getTemperature();
        radiator.on();
    }

    public void stop() {
        LOGGER.info("Stopping Cooling Controller..");
        radiator.off();
    }
}
