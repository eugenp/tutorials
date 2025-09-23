package com.baeldung.samesign;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SameSignIntegersUnitTest {

    @Test
    public void givenTwoPositiveNumbers_whenUsingConditionalChecks_thenReturnTrue() {
        int a = 5;
        int b = 3;
        
        boolean sameSign = (a >= 0 && b >= 0) || (a < 0 && b < 0);
        
        assertTrue(sameSign);
    }

    @Test
    public void givenPositiveAndNegativeNumbers_whenUsingConditionalChecks_thenReturnFalse() {
        int a = 5;
        int b = -3;
        
        boolean sameSign = (a >= 0 && b >= 0) || (a < 0 && b < 0);
        
        assertFalse(sameSign);
    }

    @Test
    public void givenTwoNegativeNumbers_whenUsingMultiplication_thenReturnTrue() {
        int a = -5;
        int b = -3;
        
        boolean sameSign = (a * b) > 0;
        
        assertTrue(sameSign);
    }

    @Test
    public void givenZeroAndAnyNumber_whenUsingMultiplication_thenReturnFalse() {
        int a = 0;
        int b = 5;
        
        boolean sameSign = (a * b) > 0;
        
        assertFalse(sameSign);
    }

    @Test
    public void givenTwoPositiveNumbers_whenUsingBitwiseXOR_thenReturnTrue() {
        int a = 5;
        int b = 3;
        
        boolean sameSign = (a ^ b) >= 0;
        
        assertTrue(sameSign);
    }

    @Test
    public void givenPositiveAndNegativeNumbers_whenUsingBitwiseXOR_thenReturnFalse() {
        int a = 5;
        int b = -3;
        
        boolean sameSign = (a ^ b) >= 0;
        
        assertFalse(sameSign);
    }

    @Test
    public void givenTwoNegativeNumbers_whenUsingMathSignum_thenReturnTrue() {
        int a = -5;
        int b = -3;
        
        boolean sameSign = Math.signum(a) == Math.signum(b);
        
        assertTrue(sameSign);
    }

    @Test
    public void givenZeroAndPositiveNumber_whenUsingMathSignum_thenReturnFalse() {
        int a = 0;
        int b = 5;
        
        boolean sameSign = Math.signum(a) == Math.signum(b);
        
        assertFalse(sameSign);
    }
}
