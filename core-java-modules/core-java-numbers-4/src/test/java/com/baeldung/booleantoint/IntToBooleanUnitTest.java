package com.baeldung.booleantoint;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.api.Test;

public class IntToBooleanUnitTest {

    boolean intToBoolean(int theInt) {
        return theInt > 0;
    }

    boolean intToBooleanWithThrowing(int theInt) {
        if (theInt == 1) {
            return true;
        }
        if (theInt == 0) {
            return false;
        }
        throw new IllegalArgumentException("Only 0 or 1 is allowed.");
    }

    @Test
    void whenUsingBooleanExpression_thenGetExpectedResult() {
        assertTrue(intToBoolean(42));
        assertFalse(intToBoolean(0));
        assertFalse(intToBoolean(-42));
    }

    @Test
    void whenUsingIfBooleanExpression_thenGetExpectedResult() {
        assertTrue(intToBooleanWithThrowing(1));
        assertFalse(intToBooleanWithThrowing(0));
        assertThrows(IllegalArgumentException.class, () -> intToBooleanWithThrowing(42));
        assertThrows(IllegalArgumentException.class, () -> intToBooleanWithThrowing(-42));
    }

    @Test
    void whenUsingBooleanUtilsToBoolean_thenGetExpectedResult() {
        // calling BooleanUtils.toBoolean(int value)
        assertTrue(BooleanUtils.toBoolean(1));
        assertFalse(BooleanUtils.toBoolean(0));
        assertTrue(BooleanUtils.toBoolean(42));
        assertTrue(BooleanUtils.toBoolean(-42));

        // calling BooleanUtils.toBoolean(int value, int trueValue, int falseValue)
        int trueValue = 1;
        int falseValue = 0;

        assertTrue(BooleanUtils.toBoolean(1, trueValue, falseValue));
        assertFalse(BooleanUtils.toBoolean(0, trueValue, falseValue));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.toBoolean(42, trueValue, falseValue));
        assertThrows(IllegalArgumentException.class, () -> BooleanUtils.toBoolean(-42, trueValue, falseValue));
    }

}