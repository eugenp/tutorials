package com.baeldung.log4j;

import org.apache.log4j.Logger;

public class NoAppenderExample {

    private final static Logger logger = Logger.getLogger(NoAppenderExample.class);

    public static void main(String[] args) {

        //Setup default appender
        //BasicConfigurator.configure();

        //Define path to configuration file
        //PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        logger.info("Info log message");
    }
}
