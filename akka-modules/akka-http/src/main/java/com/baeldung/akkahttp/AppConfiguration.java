package com.baeldung.akkahttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfiguration {

    static Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    public static void main(String[] args) {
        LOGGER.debug("THIS IS DEBUG LEVEL");
        LOGGER.info("THIS IS INFO LEVEL");
        LOGGER.warn("THIS IS WARN LEVEL");
        LOGGER.error("THIS IS ERROR LEVEL");
    }

}
