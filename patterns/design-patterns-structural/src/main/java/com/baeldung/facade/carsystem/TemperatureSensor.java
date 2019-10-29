package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperatureSensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureSensor.class);

    public void getTemperature(){
        LOGGER.info("Getting temperature from the sensor..");
    }

}
