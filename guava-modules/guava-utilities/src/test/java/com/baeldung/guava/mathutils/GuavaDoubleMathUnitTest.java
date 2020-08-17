package com.baeldung.guava.mathutils;

import static org.junit.Assert.*;

import java.math.RoundingMode;

import org.junit.Test;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;

public class GuavaDoubleMathUnitTest {

    @Test
    public void whenFactorailDouble_shouldFactorialThemAndReturnTheResultIfInDoubleRange() {
        double result = DoubleMath.factorial(5);
        assertEquals(120, result, 0);
    }

    @Test
    public void whenFactorailDouble_shouldFactorialThemAndReturnDoubkeInfIfNotInDoubletRange() {
        double result = DoubleMath.factorial(Integer.MAX_VALUE);
        assertEquals(Double.POSITIVE_INFINITY, result, 0);
    }

    @Test
    public void whenFuzzyCompareDouble_shouldReturnZeroIfInRange() {
        int result = DoubleMath.fuzzyCompare(4, 4.05, 0.6);
        assertEquals(0, result);
    }

    @Test
    public void whenFuzzyCompareDouble_shouldReturnNonZeroIfNotInRange() {
        int result = DoubleMath.fuzzyCompare(4, 5, 0.1);
        assertEquals(-1, result);
    }

    @Test
    public void whenFuzzyEqualDouble_shouldReturnZeroIfInRange() {
        boolean result = DoubleMath.fuzzyEquals(4, 4.05, 0.6);
        assertTrue(result);
    }

    @Test
    public void whenFuzzyEqualDouble_shouldReturnNonZeroIfNotInRange() {
        boolean result = DoubleMath.fuzzyEquals(4, 5, 0.1);
        assertFalse(result);
    }

    @Test
    public void whenMathematicalIntDouble_shouldReturnTrueIfInRange() {
        boolean result = DoubleMath.isMathematicalInteger(5);
        assertTrue(result);
    }

    @Test
    public void whenMathematicalIntDouble_shouldReturnFalseIfNotInRange() {
        boolean result = DoubleMath.isMathematicalInteger(5.2);
        assertFalse(result);
    }

    @Test
    public void whenIsPowerOfTwoDouble_shouldReturnTrueIfIsPowerOfTwo() {
        boolean result = DoubleMath.isMathematicalInteger(4);
        assertTrue(result);
    }

    @Test
    public void whenIsPowerOfTwoDouble_shouldReturnFalseIsNotPowerOfTwoe() {
        boolean result = DoubleMath.isMathematicalInteger(5.2);
        assertFalse(result);
    }

    @Test
    public void whenLog2Double_shouldReturnResult() {
        double result = DoubleMath.log2(4);
        assertEquals(2, result, 0);
    }
    
    
    @Test
    public void whenLog2DoubleValues_shouldLog2ThemAndReturnTheResultForCeilingRounding() {
        int result = DoubleMath.log2(30, RoundingMode.CEILING);
        assertEquals(5, result);
    }

    @Test
    public void whenLog2DoubleValues_shouldog2ThemAndReturnTheResultForFloorRounding() {
        int result = DoubleMath.log2(30, RoundingMode.FLOOR);
        assertEquals(4, result);
    }

    @Test(expected = ArithmeticException.class)
    public void whenLog2DoubleValues_shouldThrowArithmeticExceptionIfRoundingNotDefinedButNecessary() {
        DoubleMath.log2(30, RoundingMode.UNNECESSARY);
    }

}
