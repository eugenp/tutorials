package com.baeldung.casting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Animal {
    private static final Logger LOGGER = LoggerFactory.getLogger(Animal.class);

    public void eat() {
        LOGGER.info("animal is eating");
    }

}
