package com.baeldung.camel.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IllegalArgumentExceptionThrowingProcessorUnitTest {

    @Test
    void whenProcessed_thenIllegalArgumentExceptionRaisedSuccessfully() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IllegalArgumentExceptionThrowingProcessor().process(null);
        });
    }

}
