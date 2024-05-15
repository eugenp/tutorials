package com.baeldung.getbit;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GetABitFromIntegralValueUnitTest {
    @Test
    public void givenAByte_whenUsingAHardCodedMask_thenGetBitValue() {
        byte val1 = 0b0110_0100;
        byte val2 = 0b0110_0010;
        byte mask = 0b0000_0100;
        boolean isSet1 = (val1 & mask) > 0;
        boolean isSet2 = (val2 & mask) > 0;

        assertTrue(isSet1);
        assertFalse(isSet2);
    }

    @Test
    public void givenAnIntValue_whenUsingACalculatedMask_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        int mask = 1 << pos;
        boolean isSet = (val & mask) > 0;

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingALeftShiftedValue1_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = ((val << (31 - pos)) < 0);

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingALeftShiftedValue2_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = ((val << (~pos & 31)) < 0);

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingALeftShiftedValue3_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = ((val << ~pos) < 0);

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingARightShiftedValue_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = ((val >> pos) & 1) == 1;

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingBigInteger_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = BigInteger.valueOf(val).testBit(pos);

        assertTrue(isSet);
    }

}
