package com.boot.camel.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.camel.boot.exception.IllegalArgumentExceptionThrowingProcessor;

class IllegalArgumentExceptionThrowingProcessorUnitTest {

    @Test
    void whenProcessed_thenIllegalArgumentExceptionRaisedSuccessfully() {
        assertThrows(IllegalArgumentException.class, () -> {
            new IllegalArgumentExceptionThrowingProcessor().process(null);
        });
    }

}
