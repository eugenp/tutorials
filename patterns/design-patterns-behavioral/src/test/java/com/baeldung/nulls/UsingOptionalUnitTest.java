package com.baeldung.nulls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsingOptionalUnitTest {

    private UsingOptional classUnderTest;

    @BeforeEach
    public void setup() {
        classUnderTest = new UsingOptional();
    }

    @Test
    public void whenArgIsFalse_thenReturnEmptyResponse() {

        Optional<Object> result = classUnderTest.process(false);

        assertFalse(result.isPresent());
    }

    @Test
    public void whenArgIsTrue_thenReturnValidResponse() {

        Optional<Object> result = classUnderTest.process(true);

        assertTrue(result.isPresent());
    }

    @Test
    public void whenArgIsFalse_thenChainResponseAndThrowException() {

        assertThrows(Exception.class, () -> classUnderTest.process(false).orElseThrow(() -> new Exception()));
    }
}