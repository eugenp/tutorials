package com.baeldung.printassertionresults;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(TestServiceTest.class);

    TestService service = new TestService();

    @Test
    void whenSuccessfulCall_thenSuccessMessagePrintedIntoLog() {
        logger.info("Testing successful call...");
        assertTrue(service.successfulCall(), "Service should return true for successful call");
        logger.info("✓ Successful call assertion passed");
    }

    @Disabled
    @Test
    void whenFailedCall_thenFailureMessagePrintedIntoLog() {
        logger.info("Testing failed call...");
        assertTrue(service.failedCall(), "Service should return true for failed call");
    }

    @Disabled
    @Test
    void whenRunMultipleAssertionsWithLogging_thenAllTheLogsShouldBePrintedAndFailureExceptionsRethrown() {
        LoggingAssertions.assertAll(
          new AssertionWithMessage(
            () -> assertTrue(service.successfulCall()),
            "Successful call should return true"),
          new AssertionWithMessage(
            () -> assertTrue(service.failedCall()),
            "Failed call should return true"),
          new AssertionWithMessage(
            () -> assertThrows(RuntimeException.class, service::exceptionCall),
            "Exception call should throw RuntimeException")
        );
    }
}