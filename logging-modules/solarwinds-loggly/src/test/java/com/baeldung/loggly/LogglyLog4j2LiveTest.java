package com.baeldung.loggly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogglyLog4j2LiveTest {

    private static final Logger logger = LogManager.getLogger(LogglyLog4j2LiveTest.class);

    @Test
    void givenLoggly_whenLogEvent_thenPushEventToLoggly() {
        logger.info("This is a log4j2 test info message");
        logger.debug("This is a log4j2 test debug message");
        logger.error("This is a log4j2 test error message");
    }
}
