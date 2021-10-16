package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlingNullValuesUnitTest {

     static double getDoubleUsingSwitchNullCase(Object o) {
        return switch (o) {
            case String s -> Double.parseDouble(s);
            case null -> 0d;
            default -> 0d;
        };
    }

    static double getDoubleUsingSwitchTotalType(Object o) {
        return switch (o) {
            case String s -> Double.parseDouble(s);
            case Object ob -> 0d;
        };
    }

    @Test
    void givenNullCaseInSwitch_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitchNullCase("10"));
    }

    @Test
    void givenTotalTypeInSwitch_whenUsingNullArgument_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleUsingSwitchNullCase(null));
    }

    @Test
    void givenTotalTypeInSwitch_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitchTotalType("10"));
    }

    @Test
    void givenNullCaseInSwitch_whenUsingNullArgument_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleUsingSwitchTotalType(null));
    }

}
