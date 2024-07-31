package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FuelPump {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuelPump.class);

    public void pump() {
        LOGGER.info("Fuel Pump is pumping fuel..");
    }
}
