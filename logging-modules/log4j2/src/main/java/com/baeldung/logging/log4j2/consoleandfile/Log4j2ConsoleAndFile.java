package com.baeldung.logging.log4j2.consoleandfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2ConsoleAndFile {

    private static final Logger logger = LogManager.getLogger(Log4j2ConsoleAndFile.class);

    // To enable the console and file appender, just rename the log4j2 file, under /src/main/resources, to log4j2.properties
    // Reason: Having the log4j2.properties file overrides the log4j2.xml file, so that other appender classes would not work
    public static void main(String[] args) {
        logger.info("Hello World!");
        logger.debug("Hello World!");
    }
}
