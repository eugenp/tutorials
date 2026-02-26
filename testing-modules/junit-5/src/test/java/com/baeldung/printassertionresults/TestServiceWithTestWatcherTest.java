package com.baeldung.printassertionresults;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestResultLogger.class)
class TestServiceWithTestWatcherTest {

    TestService service = new TestService();

    @Test
    void whenSuccessfulCall_thenTrueShouldBeReturned() {
        assertTrue(service.successfulCall());
    }

    @Test
    void whenExceptionCall_thenExpectedExceptionShouldBeThrown() {
        assertThrows(RuntimeException.class, service::exceptionCall);
    }

    @Disabled
    @Test
    void whenFailedCall_thenTrueShouldBeReturned() {
        assertTrue(service.failedCall());
    }
}