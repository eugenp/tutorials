package com.baeldung.oroperators;

import org.apache.commons.lang3.BitField;
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
    public void givenAnIntValue_whenUsingMultiPos_thenGetSameResult() {
        int val = 0b0110_0100;
        for (int i = 6; i < Integer.SIZE; i++) {
            int pos = 1 << i | 2;
            int mask = 1 << pos;
            boolean isSet = (val & mask) > 0;

            assertTrue(isSet);
        }
    }

    @Test
    public void givenALongValue_whenUsingACalculatedMask_thenGetBitValue() {
        long val = 0b0110_0100;
        int pos = 2;
        long mask = 1L << pos;
        boolean isSet = (val & mask) > 0;

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingALeftShiftedValue_thenGetBitValue() {
        int val = 0b0110_0100;
        int pos = 2;
        boolean isSet = ((val << ~pos) < 0);

        assertTrue(isSet);
    }

    @Test
    public void givenALongValue_whenUsingALeftShiftedValue_thenGetBitValue() {
        long val = 0b0110_0100;
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
    public void givenALongValue_whenUsingARightShiftedValue_thenGetBitValue() {
        long val = 0b0110_0100;
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

    @Test
    public void givenALongValue_whenUsingBigInteger_thenGetBitValue() {
        long val = 0b0110_0100;
        int pos = 2;
        boolean isSet = BigInteger.valueOf(val).testBit(pos);

        assertTrue(isSet);
    }

    @Test
    public void givenAnIntValue_whenUsingCommonsLang_thenGetBitValue() {
        int val = 0b0110_0100;
        int mask = 0b0000_1100;
        BitField bitField = new BitField(mask);
        boolean isSet = bitField.isSet(val);

        assertTrue(isSet);
    }
}
