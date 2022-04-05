package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Radiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Radiator.class);

    public void on(){
        LOGGER.info("Radiator switched on!");
    }

    public void off(){
        LOGGER.info("Radiator switched off!");
    }

    public void setSpeed(Integer speed){
        LOGGER.info("Setting radiator speed to {}",speed);
    }
}
