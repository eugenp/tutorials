package com.baeldung;

import java.util.logging.Logger;

public class BusinessLogic {

    static void julLog(Logger julLogger) {
        julLogger.info("This is a JUL info log message!");
        julLogger.warning("This is a JUL warning log message!");
    }

    static void slf4jLog(org.slf4j.Logger logger) {
        logger.info("This is an SLF4J info log message!");
        logger.error("This is an SLF4J error log message!");
    }

}
