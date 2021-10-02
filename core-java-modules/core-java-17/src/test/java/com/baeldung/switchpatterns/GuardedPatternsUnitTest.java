package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardedPatternsUnitTest {

    static double getDoubleValueUsingIf(Object o) {
        return switch (o) {
            case String s -> {
                if (s.length() > 0) {
                    yield Double.parseDouble(s);
                } else {
                    yield 0d;
                }
            }
            default -> 0d;
        };
    }

    static double getDoubleValueUsingGuardedPatterns(Object o) {
        return switch (o) {
            case String s && s.length() > 0 -> Double.parseDouble(s);
            default -> 0d;
        };
    }

    @Test
    void givenIfImplementation_whenUsingEmptyString_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleValueUsingIf(""));
    }

    @Test
    void givenIfImplementation_whenUsingNonEmptyString_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleValueUsingIf("10"));
    }

    @Test
    void givenPatternsImplementation_whenUsingEmptyString_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleValueUsingGuardedPatterns(""));
    }

    @Test
    void givenPatternsImplementation_whenUsingNonEmptyString_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleValueUsingGuardedPatterns("10"));
    }

}
