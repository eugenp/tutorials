package com.baeldung.log4j2.consoleandfile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jConsoleAndFile {

    private static final Logger logger = LogManager.getLogger(Log4jConsoleAndFile.class);

    public static void main(String[] args) {
        logger.info("Hello World!");
        logger.debug("Hello World!");
    }
}
