package com.baeldung.generics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Building {
    private static final Logger LOGGER = LoggerFactory.getLogger(Building.class);

    public void paint() {
        LOGGER.debug("Painting Building");
    }
}
