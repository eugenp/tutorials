package com.baeldung.facade.carsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalyticConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalyticConverter.class);

    public void on() {
        LOGGER.info("Catalytic Converter switched on!");
    }

    public void off() {
        LOGGER.info("Catalytic Converter switched off!");
    }
}
