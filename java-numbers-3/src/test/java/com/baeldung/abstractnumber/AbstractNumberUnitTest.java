package com.baeldung.abstractnumber;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbstractNumberUnitTest {

    private final static double DOUBLE_VALUE = 9999.999;
    private final static float FLOAT_VALUE = 101.99F;
    private final static long LONG_VALUE = 1000L;
    private final static int INTEGER_VALUE = 100;
    private final static short SHORT_VALUE = 127;
    private final static byte BYTE_VALUE = 120;

    @Test
    public void givenDoubleValue_whenShortValueUsed_thenShortValueReturned() {
        Double doubleValue = Double.valueOf(DOUBLE_VALUE);
        assertEquals(9999, doubleValue.shortValue());
    }

    @Test
    public void givenFloatValue_whenByteValueUsed_thenByteValueReturned() {
        Float floatValue = Float.valueOf(FLOAT_VALUE);
        assertEquals(101, floatValue.byteValue());
    }

    @Test
    public void givenLongValue_whenInitValueUsed_thenInitValueReturned() {
        Long longValue = Long.valueOf(LONG_VALUE);
        assertEquals(1000, longValue.intValue());
    }

    @Test
    public void givenIntegerValue_whenLongValueUsed_thenLongValueReturned() {
        Integer integerValue = Integer.valueOf(INTEGER_VALUE);
        assertEquals(100, integerValue.longValue());
    }

    @Test
    public void givenShortValue_whenFloatValueUsed_thenFloatValueReturned() {
        Short shortValue = Short.valueOf(SHORT_VALUE);
        assertEquals(127.0F, shortValue.floatValue(), 0);
    }

    @Test
    public void givenByteValue_whenDoubleValueUsed_thenDoubleValueReturned() {
        Byte byteValue = Byte.valueOf(BYTE_VALUE);
        assertEquals(120.0, byteValue.doubleValue(), 0);
    }

}
