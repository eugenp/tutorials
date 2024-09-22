package com.baeldung.loggly;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LogglyLog4jUnitTest {

//    Logger logger = LoggerFactory.getLogger(LogglyLog4jUnitTest.class);
    Logger logger = Logger.getLogger(LogglyLog4jUnitTest.class);

    @Test
    void givenLoggly_thenLogError_thenPushErrorEventToLoggly() {
        logger.info("Entering Test");
        logger.error("This is a test error");

    }


}
