package com.baeldung.loggly;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogglyLog4jUnitTest {

    private static final Logger logger = Logger.getLogger(LogglyLog4jUnitTest.class);

    @Test
    void givenLoggly_whenLogEvent_thenPushEventToLoggly() {
        logger.info("This is a log4j 1 test info message");
        logger.debug("This is a log4j 1 test debug message");
        logger.error("This is a log4j 1 test error message");
    }
}
