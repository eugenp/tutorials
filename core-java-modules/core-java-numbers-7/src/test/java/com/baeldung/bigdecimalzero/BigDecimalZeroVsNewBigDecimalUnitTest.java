package com.baeldung.bigdecimalzero;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BigDecimalZeroVsNewBigDecimalUnitTest {
    @Test
    void whenComparingZeroAndNewBigDecimal_thenGetExpectedResult() {
        BigDecimal bd1 = new BigDecimal("42.00");
        BigDecimal bd2 = new BigDecimal("42.0000");
        assertEquals(0, bd1.compareTo(bd2));

        assertNotEquals(bd1, bd2);

        BigDecimal zero0 = new BigDecimal(0);
        assertNotEquals(zero0, new BigDecimal("0.000"));

        BigDecimal zero = BigDecimal.ZERO;
        assertEquals(zero, zero0);
    }

    @Test
    void whenCallingBigDecimalZero_thenAlwaysGetTheSameObject() {
        BigDecimal z1 = BigDecimal.ZERO;
        BigDecimal z2 = BigDecimal.ZERO;
        assertSame(z1, z2);
    }

    @Test
    void whenCallingNewBigDecimal_thenAlwaysGetTheSameObject() {
        BigDecimal z1 = new BigDecimal(0);
        BigDecimal z2 = new BigDecimal(0);
        assertNotSame(z1, z2);
    }

}