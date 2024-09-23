package com.baeldung.loggly;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogglyLog4jUnitTest {

    Logger logger = Logger.getLogger(LogglyLog4jUnitTest.class);

    @Test
    void givenLoggly_thenLogError_thenPushErrorEventToLoggly() {
        logger.info("This is a test info message");
        logger.debug("This is a test debug message");
        logger.error("This is a test error message");
    }
}
