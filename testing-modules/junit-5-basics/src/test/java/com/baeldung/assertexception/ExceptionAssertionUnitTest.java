package com.baeldung.assertexception;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ExceptionAssertionUnitTest {
    @Test
    public void whenExceptionThrown_thenAssertionSucceeds() {
        String test = null;
        assertThrows(NullPointerException.class, () -> {
            test.length();
        });
    }

    @Test
    public void whenDerivedExceptionThrown_thenAssertionSucceds() {
        String test = null;
        assertThrows(RuntimeException.class, () -> {
            test.length();
        });
    }
}