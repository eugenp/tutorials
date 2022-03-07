package com.baeldung.convertLongToInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConvertLongToIntUnitTest {

    @Test
    void longToInt() {
        long number = 186762L;
        int expected = 186762;

        assertEquals(expected, ConvertLongToInt.longToIntCast(number));
        assertEquals(expected, ConvertLongToInt.longToIntJavaWithMath(number));
        assertEquals(expected, ConvertLongToInt.longToIntJavaWithLambda(number));
        assertEquals(expected, ConvertLongToInt.longToIntBoxingValues(number));
        assertEquals(expected, ConvertLongToInt.longToIntGuava(number));
        assertEquals(expected, ConvertLongToInt.longToIntGuavaSaturated(number));
        assertEquals(expected, ConvertLongToInt.longToIntWithBigDecimal(number));
    }

}