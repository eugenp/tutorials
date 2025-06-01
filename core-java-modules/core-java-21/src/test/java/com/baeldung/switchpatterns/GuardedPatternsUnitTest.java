package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.baeldung.switchpatterns.GuardedPatterns.*;

class GuardedPatternsUnitTest {

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
