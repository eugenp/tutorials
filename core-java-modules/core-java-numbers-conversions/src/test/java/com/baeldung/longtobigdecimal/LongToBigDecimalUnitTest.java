package com.baeldung.longtobigdecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class LongToBigDecimalUnitTest {
    @Test
    void whenUsingTheConstructor_thenGetTheExpectedBigDecimal() {
        Long num4 = 4L;
        BigDecimal result4 = new BigDecimal(num4);
        assertEquals(new BigDecimal("4"), result4);

        Long num42 = -42L;
        BigDecimal result42 = new BigDecimal(num42);
        assertEquals(new BigDecimal("-42"), result42);
    }

    @Test
    void whenUsingTheValueOf_thenGetTheExpectedBigDecimal() {
        Long num4 = 4L;
        BigDecimal result4 = BigDecimal.valueOf(num4);
        assertEquals(new BigDecimal("4"), result4);

        Long num42 = -42L;
        BigDecimal result42 = BigDecimal.valueOf(num42);
        assertEquals(new BigDecimal("-42"), result42);
    }

    @Test
    void when0To10AndUsingTheValueOfMethod_thenGetTheSameBigDecimalObject() {
        Long num4 = 4L;
        BigDecimal bd4_1 = BigDecimal.valueOf(num4);
        BigDecimal bd4_2 = BigDecimal.valueOf(num4);
        BigDecimal bd4_3 = BigDecimal.valueOf(num4);
        assertSame(bd4_1, bd4_2);
        assertSame(bd4_2, bd4_3);

        Long num42 = -42L;
        BigDecimal bd42_1 = BigDecimal.valueOf(num42);
        BigDecimal bd42_2 = BigDecimal.valueOf(num42);
        BigDecimal bd42_3 = BigDecimal.valueOf(num42);
        assertNotSame(bd42_1, bd42_2);
        assertNotSame(bd42_1, bd42_3);
        assertNotSame(bd42_2, bd42_3);
    }

    @Test
    void when0To10AndUsingTheConstructor_thenGetTheDifferentBigDecimalObjects() {
        Long num4 = 4L;
        BigDecimal result1 = new BigDecimal(num4);
        BigDecimal result2 = new BigDecimal(num4);
        BigDecimal result3 = new BigDecimal(num4);
        assertNotSame(result1, result2);
        assertNotSame(result2, result3);
        assertNotSame(result1, result3);

        Long num42 = -42L;
        BigDecimal bd42_1 = new BigDecimal(num42);
        BigDecimal bd42_2 = new BigDecimal(num42);
        BigDecimal bd42_3 = new BigDecimal(num42);
        assertNotSame(bd42_1, bd42_2);
        assertNotSame(bd42_1, bd42_3);
        assertNotSame(bd42_2, bd42_3);
    }
}