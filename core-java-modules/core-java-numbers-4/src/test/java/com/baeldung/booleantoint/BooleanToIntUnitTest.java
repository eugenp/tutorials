package com.baeldung.booleantoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanToIntUnitTest {
    @Test
    void givenBooleanPrimitiveValue_ThenReturnInt() {
        assertEquals(1, BooleanToInt.booleanPrimitiveToInt(true));
        assertEquals(0, BooleanToInt.booleanPrimitiveToInt(false));
    }

    @Test
    void givenBooleanPrimitiveValue_ThenReturnIntTernary() {
        assertEquals(1, BooleanToInt.booleanPrimitiveToIntTernary(true));
        assertEquals(0, BooleanToInt.booleanPrimitiveToIntTernary(false));
    }

    @Test
    void givenBooleanObject_ThenReturnInt() {
        assertEquals(0, BooleanToInt.booleanObjectToInt(false));
        assertEquals(1, BooleanToInt.booleanObjectToInt(true));
    }

    @Test
    void givenBooleanObject_ThenReturnIntInverse() {
        assertEquals(0, BooleanToInt.booleanObjectToIntInverse(false));
        assertEquals(1, BooleanToInt.booleanObjectToIntInverse(true));
    }

    @Test
    void givenBooleanObject_ThenReturnIntUsingClassMethod() {
        assertEquals(0, BooleanToInt.booleanObjectMethodToInt(false));
        assertEquals(1, BooleanToInt.booleanObjectMethodToInt(true));
    }

    @Test
    void givenBooleanObject_ThenReturnIntUsingClassMethodInverse() {
        assertEquals(0, BooleanToInt.booleanObjectMethodToIntInverse(false));
        assertEquals(1, BooleanToInt.booleanObjectMethodToIntInverse(true));
    }

    @Test
    void givenBoolean_ThenReturnIntUsingBooleanUtils() {
        assertEquals(0, BooleanToInt.booleanUtilsToInt(false));
        assertEquals(1, BooleanToInt.booleanUtilsToInt(true));
    }

    @Test
    void givenBoolean_ThenReturnIntUsingBitwiseOperators() {
        assertEquals(0, BooleanToInt.bitwiseBooleanToInt(false));
        assertEquals(1, BooleanToInt.bitwiseBooleanToInt(true));
    }
}
