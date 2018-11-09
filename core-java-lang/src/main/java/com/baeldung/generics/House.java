package com.baeldung.generics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class House extends Building {
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);

    public void paint() {
        LOGGER.info("Painting House");
    }
}
