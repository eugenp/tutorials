package com.baeldung.assertexception;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionAssertionUnitTest {

    private static final Logger LOG = Logger.getLogger(ExceptionAssertionUnitTest.class.getName());

    @Test
    void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NumberFormatException.class, () -> Integer.parseInt("1a"));

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenDerivedExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(RuntimeException.class, () -> Integer.parseInt("1a"));

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenABlock_whenExecutes_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            Integer.parseInt("100");
        });
    }

    private <T extends Exception> void assertSpecificExceptionIsNotThrown(Class<T> exceptionClass, Executable executable) {
        try {
            executable.execute();
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                fail(e.getClass().getSimpleName() + " was thrown");
            } else {
                // Any other exception types are ignored and test passes!
                // Logging it here for debugging purpose
                LOG.info("Caught exception: " + e.getClass().getName() + ", but ignoring since it it not an instance of " + exceptionClass.getName());
            }
        }
    }

    @Test
    void givenASpecificExceptionType_whenBlockExecutes_thenEnsureThatExceptionIsNotThrown() {
        assertSpecificExceptionIsNotThrown(IllegalArgumentException.class, () -> {
            int i = 100 / 10;
        });
    }

}
