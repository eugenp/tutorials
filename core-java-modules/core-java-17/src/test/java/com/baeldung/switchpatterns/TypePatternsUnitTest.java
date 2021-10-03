package com.baeldung.switchpatterns;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TypePatternsUnitTest {

    static double getDoubleUsingIf(Object o) {
        double result;

        if (o instanceof Integer) {
            result = ((Integer) o).doubleValue();
        } else if (o instanceof Float) {
            result = ((Float) o).doubleValue();
        } else if (o instanceof String) {
            result = Double.parseDouble(((String) o));
        } else {
            result = 0d;
        }

        return result;
    }

    static double getDoubleUsingSwitch(Object o) {
        return switch (o) {
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s -> Double.parseDouble(s);
            default -> 0d;
        };
    }

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
