package com.baeldung.loggly;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogglyLogbackLiveTest {

    Logger logger = LoggerFactory.getLogger(LogglyLogbackLiveTest.class);

    @Test
    void givenLoggly_whenLogEvent_thenPushEventToLoggly() {
        logger.info("This is a logback test info message");
        logger.debug("This is a logback test debug message");
        logger.error("This is a logback test error message");
    }
}
