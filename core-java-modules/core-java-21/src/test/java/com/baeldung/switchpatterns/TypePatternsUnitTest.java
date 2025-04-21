package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.baeldung.switchpatterns.TypePatterns.*;

class TypePatternsUnitTest {

    @Test
    void givenIfImplementation_whenUsingIntegerAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingIf(10));
    }

    @Test
    void givenIfImplementation_whenUsingDoubleAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingIf(10.0f));
    }

    @Test
    void givenIfImplementation_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingIf("10"));
    }

    @Test
    void givenIfImplementation_whenUsingCharAsArgument_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleUsingIf('c'));
    }

    @Test
    void givenSwitchImplementation_whenUsingIntegerAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitch(10));
    }

    @Test
    void givenSwitchImplementation_whenUsingDoubleAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitch(10.0f));
    }

    @Test
    void givenSwitchImplementation_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, getDoubleUsingSwitch("10"));
    }

    @Test
    void givenSwitchImplementation_whenUsingCharAsArgument_thenDoubleIsReturned() {
        assertEquals(0d, getDoubleUsingSwitch('c'));
    }

}
