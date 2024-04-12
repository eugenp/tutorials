package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.baeldung.switchpatterns.ParenthesizedPatterns.*;

class ParenthesizedPatternsUnitTest {

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
