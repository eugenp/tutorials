package com.baeldung.nulls;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsingOptionalUnitTest {

    private UsingOptional dataObject;

    @BeforeEach
    public void setup() {
        dataObject = new UsingOptional();
    }

    @Test
    void whenArgIsFalse_thenReturnEmptyResponse() {
        Optional<Object> result = dataObject.process(false);

        assertFalse(result.isPresent());
    }

    @Test
    void whenArgIsTrue_thenReturnValidResponse() {
        Optional<Object> result = dataObject.process(true);

        assertTrue(result.isPresent());
    }

    @Test
    void whenArgIsFalse_thenChainResponseAndThrowException() {
        assertThrows(Exception.class, () -> dataObject.process(false).orElseThrow(Exception::new));
    }

    @Test()
    void givenEmptyList_whenFindFirst_getDefaultValue() {
        assertTrue(dataObject.findFirst().equalsIgnoreCase(UsingOptional.DEFAULT_VALUE));
    }

    @Test()
    void givenEmptyList_whenFindOptionalFirst_returnsEmptyOptional() {
        assertFalse(dataObject.findOptionalFirst().isPresent());
    }

    @Test()
    void whenOptionalListFirst_returnsEmptyOptional() {
        assertFalse(dataObject.optionalListFirst().isPresent());
    }
}