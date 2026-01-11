package com.baeldung.printassertionresults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAssertions {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAssertions.class);

    public static void assertAll(AssertionWithMessage... assertions) {
        boolean failed = false;
        for (AssertionWithMessage assertion : assertions) {
            try {
                assertion.doAssert();
                logger.info("✓ {}", assertion.getMessage());
            } catch (AssertionError e) {
                failed = true;
                logger.error("✗ {} - {}", assertion.getMessage(), e.getMessage());
            }
        }

        if (failed) {
            /*
            * Critical: Re-throw to maintain test failure behavior
            * */
            throw new AssertionError("One of the assertions was failed. See logs for details");
        }
    }
}
