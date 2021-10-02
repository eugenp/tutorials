package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParenthesizedPatternsUnitTest {

    static double getDoubleValueUsingIf(Object o) {
        return switch (o) {
            case String s -> {
                if (s.length() > 0) {
                    if (s.contains("#") || s.contains("@")) {
                        yield 0d;
                    } else {
                        yield Double.parseDouble(s);
                    }
                } else {
                    yield 0d;
                }
            }
            default -> 0d;
        };
    }

    static double getDoubleValueUsingParenthesizedPatterns(Object o) {
        return switch (o) {
            case String s && s.length() > 0 && !(s.contains("#") || s.contains("@")) -> Double.parseDouble(s);
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
    void givenIfImplementation_whenStringContainsSpecialChar_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleValueUsingIf("@10"));
    }

    @Test
    void givenPatternsImplementation_whenUsingEmptyString_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleValueUsingParenthesizedPatterns(""));
    }

    @Test
    void givenPatternsImplementation_whenUsingNonEmptyString_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleValueUsingParenthesizedPatterns("10"));
    }

    @Test
    void givenPatternsImplementation_whenStringContainsSpecialChar_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleValueUsingParenthesizedPatterns("@10"));
    }

}
