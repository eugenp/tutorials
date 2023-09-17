package com.baeldung.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BooleanUnitTest {

    @Test
    void testBooleanFromString() {
        Boolean trueVar = Boolean.valueOf("TRUE");
        Boolean falseVar = Boolean.valueOf("false");
        Boolean parsedVar = Boolean.parseBoolean("True");

        assertEquals(Boolean.TRUE, trueVar);
        assertEquals(Boolean.FALSE, falseVar);
        assertEquals(Boolean.TRUE, parsedVar);
    }

    @Test
    void testBoolean() {
        Boolean trueVar = Boolean.valueOf(true);
        Boolean falseVar = Boolean.valueOf(false);

        assertEquals(Boolean.TRUE, trueVar);
        assertEquals(Boolean.FALSE, falseVar);
    }
}
