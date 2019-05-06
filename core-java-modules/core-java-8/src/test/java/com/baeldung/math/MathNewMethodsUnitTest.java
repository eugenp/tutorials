package com.baeldung.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathNewMethodsUnitTest {

    @Test
    public void whenAddExactToInteger_thenExpectCorrectArithmeticResult() {
        assertEquals(150, Math.addExact(100, 50));                // Returns 150
    }

    @Test
    public void whenSubstractExactFromInteger_thenExpectCorrectArithmeticResult() {
        assertEquals(50, Math.subtractExact(100, 50));            // Returns 50
    }

    @Test
    public void whenDecrementExactInteger_thenExpectCorrectArithmeticResult() {
        assertEquals(99, Math.decrementExact(100));               // Returns 99
    }

    @Test
    public void whenIncrementExactToInteger_thenExpectCorrectArithmeticResult() {
        assertEquals(101, Math.incrementExact(100));              // Returns 101
    }

    @Test
    public void whenMultiplyExactTwoIntegers_thenExpectCorrectArithmeticResult() {
        assertEquals(500, Math.multiplyExact(100, 5));            // Returns 500
    }

    @Test
    public void whenNegateExactInteger_thenExpectCorrectArithmeticResult() {
        assertEquals(-100, Math.negateExact(100));                // Returns -100
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenAddToMaxInteger_thenThrowsArithmeticException() {
        Math.addExact(Integer.MAX_VALUE, 1);                      // Throws ArithmeticException
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenDecrementMinInteger_thenThrowsArithmeticException() {
        Math.decrementExact(Integer.MIN_VALUE);                   // Throws ArithmeticException
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenIncrementMaxLong_thenThrowsArithmeticException() {
        Math.incrementExact(Long.MAX_VALUE);                      // Throws ArithmeticException
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenMultiplyMaxLong_thenThrowsArithmeticException() {
        Math.multiplyExact(Long.MAX_VALUE, 2);                    // Throws ArithmeticException
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenNegateMinInteger_thenThrowsArithmeticException() {
        Math.negateExact(Integer.MIN_VALUE);                      // MinInt value: −2.147.483.648, but MaxInt Value: 2.147.483.647  => Throws ArithmeticException
    }
    
    @Test(expected = ArithmeticException.class) 
    public void whenSubstractFromMinInteger_thenThrowsArithmeticException() {
        Math.subtractExact(Integer.MIN_VALUE, 1);
    }
    
    @Test
    public void whenFloorDivTwoIntegers_thenExpectCorrectArithmeticResult() {
        assertEquals(3, Math.floorDiv(7, 2));                     // Exact quotient is 3.5 so floor(3.5) == 3
        assertEquals(-4, Math.floorDiv(-7, 2));                   // Exact quotient is -3.5 so floor(-3.5) == -4
    }
    
    @Test
    public void whenModDivTwoIntegers_thenExpectCorrectArithmeticResult() {
        assertEquals(2, Math.floorMod(5, 3));                     // Returns 2: floorMod for positive numbers returns the same as % operator
        assertEquals(1, Math.floorMod(-5, 3));                    // Returns 1 and not 2 because floorDiv(-5, 3) is -2 and not -1 and (-2*3) + (1) = -5
    }
    
    @Test
    public void whenNextDownOfDouble_thenExpectCorrectNumber() {
        double number = 3.0;
        double expected = 2.999999999999;
        double delta = 0.00000001;
        assertEquals(expected, Math.nextDown(number), delta);     // The delta defines the accepted error range
    }
    
}
