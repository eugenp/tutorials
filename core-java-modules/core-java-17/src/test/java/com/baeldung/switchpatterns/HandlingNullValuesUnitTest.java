package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlingNullValuesUnitTest {

     static double getDoubleUsingSwitch(Object o) {
        return switch (o) {
            case String s -> Double.parseDouble(s);
            case null -> 0d;
            default -> 0d;
        };
    }

    @Test
    void givenSwitchImplementation_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitch("10"));
    }

    @Test
    void givenSwitchImplementation_whenUsingNullArgument_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleUsingSwitch(null));
    }

}
