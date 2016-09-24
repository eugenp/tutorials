package com.baeldung.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To switch between logging frameworks you need only to uncomment needed framework dependencies in pom.xml
 */
public class Slf4jExample {
    private static Logger logger = LoggerFactory.getLogger(Slf4jExample.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        String variable = "Hello John";
        logger.debug("Printing variable value {} ", variable);
    }
}
