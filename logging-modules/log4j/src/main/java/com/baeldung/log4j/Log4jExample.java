package com.baeldung.log4j;


import org.apache.log4j.Logger;

public class Log4jExample {

    private final static Logger logger = Logger.getLogger(Log4jExample.class);

    public static void main(String[] args) {
        logger.trace("Trace log message");
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }

}
