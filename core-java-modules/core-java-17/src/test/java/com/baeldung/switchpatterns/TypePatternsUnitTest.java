package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypePatternsUnitTest {

    @Test
    public void givenIfImplementation_whenUsingLongAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingIf(10L));
    }

    @Test
    public void givenIfImplementation_whenUsingIntegerAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingIf(10));
    }

    @Test
    public void givenIfImplementation_whenUsingDoubleAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingIf(10.0f));
    }

    @Test
    public void givenIfImplementation_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingIf("10"));
    }

    @Test
    public void givenIfImplementation_whenUsingCharAsArgument_thenDoubleIsReturned() {
        assertEquals(0d, TypePatterns.getDoubleValueUsingIf('c'));
    }

    @Test
    public void givenSwitchImplementation_whenUsingLongAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingSwitch(10L));
    }

    @Test
    public void givenSwitchImplementation_whenUsingIntegerAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingSwitch(10));
    }

    @Test
    public void givenSwitchImplementation_whenUsingDoubleAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingSwitch(10.0f));
    }

    @Test
    public void givenSwitchImplementation_whenUsingStringAsArgument_thenDoubleIsReturned() {
        assertEquals(10d, TypePatterns.getDoubleValueUsingSwitch("10"));
    }

    @Test
    public void givenSwitchImplementation_whenUsingCharAsArgument_thenDoubleIsReturned() {
        assertEquals(0d, TypePatterns.getDoubleValueUsingSwitch('c'));
    }

}
