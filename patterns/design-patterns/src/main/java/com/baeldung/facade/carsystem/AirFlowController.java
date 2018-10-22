package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirFlowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirFlowController.class);
    private AirFlowMeter airFlowMeter = new AirFlowMeter();

    public void takeAir() {
        airFlowMeter.getMeasurements();
        LOGGER.info("Air provided!");
    }

    public void off() {
        LOGGER.info("Air controller switched off.");
    }
}
