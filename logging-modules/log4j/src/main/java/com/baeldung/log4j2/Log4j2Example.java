package com.baeldung.log4j2;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4j2Example {

    private static final Logger logger = LogManager.getLogger(Log4j2Example.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }

}
