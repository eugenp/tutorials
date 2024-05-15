package com.baeldung.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BooleanUnitTest {

    @Test
    void givenInputAsString_whenStringToBoolean_thenValidBooleanConversion() {
        assertEquals(Boolean.TRUE, Boolean.valueOf("TRUE"));
        assertEquals(Boolean.FALSE, Boolean.valueOf("false"));
        assertEquals(Boolean.TRUE, Boolean.parseBoolean("True"));
    }

    @Test
    void givenInputAsboolean_whenbooleanToBoolean_thenValidBooleanConversion() {
        assertEquals(Boolean.TRUE, Boolean.valueOf(true));
        assertEquals(Boolean.FALSE, Boolean.valueOf(false));
    }
}
