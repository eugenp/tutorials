package com.baeldung.guava.mathutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.math.RoundingMode;

import org.junit.Test;

import com.google.common.math.BigIntegerMath;

public class GuavaBigIntegerMathUnitTest {

    @Test
    public void whenPerformBinomialOnTwoIntValues_shouldReturnResult() {
        BigInteger result = BigIntegerMath.binomial(6, 3);
        assertEquals(new BigInteger("20"), result);
    }

    @Test
    public void whenProformCeilPowOfTwoBigIntegerValues_shouldReturnResult() {
        BigInteger result = BigIntegerMath.ceilingPowerOfTwo(new BigInteger("20"));
        assertEquals(new BigInteger("32"), result);
    }

    @Test
    public void whenDivideTwoBigIntegerValues_shouldDivideThemAndReturnTheResultForCeilingRounding() {
        BigInteger result = BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.CEILING);
        assertEquals(new BigInteger("4"), result);
    }

    @Test
    public void whenDivideTwoBigIntegerValues_shouldDivideThemAndReturnTheResultForFloorRounding() {
        BigInteger result = BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.FLOOR);
        assertEquals(new BigInteger("3"), result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenDivideTwoBigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        BigIntegerMath.divide(new BigInteger("10"), new BigInteger("3"), RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenFactorailInteger_shouldFactorialThemAndReturnTheResultIfInIntRange() {
        BigInteger result = BigIntegerMath.factorial(5);
        assertEquals(new BigInteger("120"), result);
    }

    @Test
    public void whenFloorPowerOfInteger_shouldReturnValue() {
        BigInteger result = BigIntegerMath.floorPowerOfTwo(new BigInteger("30"));
        assertEquals(new BigInteger("16"), result);
    }

    @Test
    public void whenIsPowOfInteger_shouldReturnTrueIfPowerOfTwo() {
        boolean result = BigIntegerMath.isPowerOfTwo(new BigInteger("16"));
        assertTrue(result);
    }

    @Test
    public void whenLog10BigIntegerValues_shouldLog10ThemAndReturnTheResultForCeilingRounding() {
        int result = BigIntegerMath.log10(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(2, result);
    }

    @Test
    public void whenLog10BigIntegerValues_shouldog10ThemAndReturnTheResultForFloorRounding() {
        int result = BigIntegerMath.log10(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(1, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenLog10BigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        BigIntegerMath.log10(new BigInteger("30"), RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenLog2BigIntegerValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = BigIntegerMath.log2(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2BigIntegerValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = BigIntegerMath.log2(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenLog2BigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        BigIntegerMath.log2(new BigInteger("30"), RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenSqrtBigIntegerValues_shouldSqrtThemAndReturnTheResultForCeilingRounding() {
        BigInteger result = BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.CEILING);
        assertEquals(new BigInteger("6"), result);
    }

    @Test
    public void whenSqrtBigIntegerValues_shouldSqrtThemAndReturnTheResultForFloorRounding() {
        BigInteger result = BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.FLOOR);
        assertEquals(new BigInteger("5"), result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenSqrtBigIntegerValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        BigIntegerMath.sqrt(new BigInteger("30"), RoundingMode.UNNECESSARY);
    }
}
