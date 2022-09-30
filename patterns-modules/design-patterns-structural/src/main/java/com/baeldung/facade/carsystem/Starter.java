package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    public void start() {
        LOGGER.info("Starting..");
    }
}
