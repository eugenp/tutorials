package com.baeldung.guava;

import static org.junit.Assert.*;

import java.math.RoundingMode;

import com.google.common.math.LongMath;
import org.junit.Test;

public class GuavaLongMathUnitTest {

    @Test
    public void whenPerformBinomialOnTwoLongValues_shouldReturnResultIfUnderLong() {
        long result = LongMath.binomial(6, 3);
        assertEquals(20L, result);
    }

    @Test
    public void whenProformCeilPowOfTwoLongValues_shouldReturnResult() {
        long result = LongMath.ceilingPowerOfTwo(20L);
        assertEquals(32L, result);
    }

    @Test
    public void whenCheckedAddTwoLongValues_shouldAddThemAndReturnTheSumIfNotOverflow() {
        long result = LongMath.checkedAdd(1L, 2L);
        assertEquals(3L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenCheckedAddTwoLongValues_shouldThrowArithmeticExceptionIfOverflow() {
        LongMath.checkedAdd(Long.MAX_VALUE, 100L);
    }

    @Test
    public void whenCheckedMultiplyTwoLongValues_shouldMultiplyThemAndReturnTheResultIfNotOverflow() {
        long result = LongMath.checkedMultiply(3L, 2L);
        assertEquals(6L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenCheckedMultiplyTwoLongValues_shouldThrowArithmeticExceptionIfOverflow() {
        LongMath.checkedMultiply(Long.MAX_VALUE, 100L);
    }

    @Test
    public void whenCheckedPowTwoLongValues_shouldPowThemAndReturnTheResultIfNotOverflow() {
        long result = LongMath.checkedPow(2L, 3);
        assertEquals(8L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void gwhenCheckedPowTwoLongValues_shouldThrowArithmeticExceptionIfOverflow() {
        LongMath.checkedPow(Long.MAX_VALUE, 100);
    }

    @Test
    public void whenCheckedSubstractTwoLongValues_shouldSubstractThemAndReturnTheResultIfNotOverflow() {
        long result = LongMath.checkedSubtract(4L, 1L);
        assertEquals(3L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void gwhenCheckedSubstractTwoLongValues_shouldThrowArithmeticExceptionIfOverflow() {
        LongMath.checkedSubtract(Long.MAX_VALUE, -100);
    }

    @Test
    public void whenDivideTwoLongValues_shouldDivideThemAndReturnTheResultForCeilingRounding() {
        long result = LongMath.divide(10L, 3L, RoundingMode.CEILING);
        assertEquals(4L, result);
    }

    @Test
    public void whenDivideTwoLongValues_shouldDivideThemAndReturnTheResultForFloorRounding() {
        long result = LongMath.divide(10L, 3L, RoundingMode.FLOOR);
        assertEquals(3L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenDivideTwoLongValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        LongMath.divide(10L, 3L, RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenFactorailLong_shouldFactorialThemAndReturnTheResultIfInIntRange() {
        long result = LongMath.factorial(5);
        assertEquals(120L, result);
    }

    @Test
    public void whenFactorailLong_shouldFactorialThemAndReturnIntMaxIfNotInIntRange() {
        long result = LongMath.factorial(Integer.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void whenFloorPowerOfLong_shouldReturnValue() {
        long result = LongMath.floorPowerOfTwo(30L);
        assertEquals(16L, result);
    }

    @Test
    public void whenGcdOfTwoLongs_shouldReturnValue() {
        long result = LongMath.gcd(30L, 40L);
        assertEquals(10L, result);
    }

    @Test
    public void whenIsPowOfLong_shouldReturnTrueIfPowerOfTwo() {
        boolean result = LongMath.isPowerOfTwo(16L);
        assertTrue(result);
    }

    @Test
    public void whenIsPowOfLong_shouldReturnFalseeIfNotPowerOfTwo() {
        boolean result = LongMath.isPowerOfTwo(20L);
        assertFalse(result);
    }

    @Test
    public void whenIsPrineOfLong_shouldReturnFalseeIfNotPrime() {
        boolean result = LongMath.isPrime(20L);
        assertFalse(result);
    }

    @Test
    public void whenLog10LongValues_shouldLog10ThemAndReturnTheResultForCeilingRounding() {
        int result = LongMath.log10(30L, RoundingMode.CEILING);
        assertEquals(2, result);
    }

    @Test
    public void whenLog10LongValues_shouldog10ThemAndReturnTheResultForFloorRounding() {
        int result = LongMath.log10(30L, RoundingMode.FLOOR);
        assertEquals(1, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenLog10LongValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        LongMath.log10(30L, RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenLog2LongValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = LongMath.log2(30L, RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2LongValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = LongMath.log2(30L, RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenLog2LongValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        LongMath.log2(30L, RoundingMode.UNNECESSARY);
    }

    @Test
    public void whenMeanTwoLongValues_shouldMeanThemAndReturnTheResult() {
        long result = LongMath.mean(30L, 20L);
        assertEquals(25L, result);
    }

    @Test
    public void whenModLongAndIntegerValues_shouldModThemAndReturnTheResult() {
        int result = LongMath.mod(30L, 4);
        assertEquals(2, result);
    }

    @Test
    public void whenModTwoLongValues_shouldModThemAndReturnTheResult() {
        long result = LongMath.mod(30L, 4L);
        assertEquals(2L, result);
    }

    @Test
    public void whenPowTwoLongValues_shouldPowThemAndReturnTheResult() {
        long result = LongMath.pow(6L, 4);
        assertEquals(1296L, result);
    }

    @Test
    public void whenSaturatedAddTwoLongValues_shouldAddThemAndReturnTheResult() {
        long result = LongMath.saturatedAdd(6L, 4L);
        assertEquals(10L, result);
    }

    @Test
    public void whenSaturatedAddTwoLongValues_shouldAddThemAndReturnIntMaxIfOverflow() {
        long result = LongMath.saturatedAdd(Long.MAX_VALUE, 1000L);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedAddTwoLongValues_shouldAddThemAndReturnIntMinIfUnderflow() {
        long result = LongMath.saturatedAdd(Long.MIN_VALUE, -1000);
        assertEquals(Long.MIN_VALUE, result);
    }

    @Test
    public void whenSaturatedMultiplyTwoLongValues_shouldMultiplyThemAndReturnTheResult() {
        long result = LongMath.saturatedMultiply(6L, 4L);
        assertEquals(24L, result);
    }

    @Test
    public void whenSaturatedMultiplyTwoLongValues_shouldMultiplyThemAndReturnIntMaxIfOverflow() {
        long result = LongMath.saturatedMultiply(Long.MAX_VALUE, 1000L);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedPowTwoLongValues_shouldPowThemAndReturnTheResult() {
        long result = LongMath.saturatedPow(6L, 2);
        assertEquals(36L, result);
    }

    @Test
    public void whenSaturatedPowTwoLongValues_shouldPowThemAndReturnIntMaxIfOverflow() {
        long result = LongMath.saturatedPow(Long.MAX_VALUE, 2);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedPowTwoLongValues_shouldPowThemAndReturnIntMinIfUnderflow() {
        long result = LongMath.saturatedPow(Long.MIN_VALUE, 3);
        assertEquals(Long.MIN_VALUE, result);
    }

    @Test
    public void whenSaturatedSubstractTwoLongValues_shouldSubstractThemAndReturnTheResult() {
        long result = LongMath.saturatedSubtract(6L, 2L);
        assertEquals(4L, result);
    }

    @Test
    public void whenSaturatedSubstractTwoLongValues_shouldSubstractwThemAndReturnIntMaxIfOverflow() {
        long result = LongMath.saturatedSubtract(Long.MAX_VALUE, -2L);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void whenSaturatedSubstractTwoLongValues_shouldSubstractThemAndReturnIntMinIfUnderflow() {
        long result = LongMath.saturatedSubtract(Long.MIN_VALUE, 3L);
        assertEquals(Long.MIN_VALUE, result);
    }

    @Test
    public void whenSqrtLongValues_shouldSqrtThemAndReturnTheResultForCeilingRounding() {
        long result = LongMath.sqrt(30L, RoundingMode.CEILING);
        assertEquals(6L, result);
    }

    @Test
    public void whenSqrtLongValues_shouldSqrtThemAndReturnTheResultForFloorRounding() {
        long result = LongMath.sqrt(30L, RoundingMode.FLOOR);
        assertEquals(5L, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenSqrtLongValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        LongMath.sqrt(30L, RoundingMode.UNNECESSARY);
    }

}
