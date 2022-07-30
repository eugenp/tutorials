package com.baeldung.log4j.consoleandfile;

import org.apache.log4j.Logger;

public class Log4jConsoleAndFile {

    private static final Logger logger = Logger.getLogger(Log4jConsoleAndFile.class);

    public static void main(String[] args) {
        logger.info("Hello World!");
        logger.debug("Hello World!");
    }
}
