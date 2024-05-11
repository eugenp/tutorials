package com.baeldung.poweroftwo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class CheckPowerOfTwoUnitTest {

    @Test
    public void givenANonePowerOfTwoNum_whenUsingLogarithm_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.isPowerOfTwoUsingLogarithm(100));
    }

    @Test
    public void givenAPowerOfTwoNum_whenUsingLogarithm_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.isPowerOfTwoUsingLogarithm(256));
    }

    @Test
    public void givenANonePowerOfTwoNum_whenUsingBitwiseOperations_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.isPowerOfTwoUsingBitwiseOperations(100));
    }

    @Test
    public void givenAPowerOfTwoNum_whenUsingBitwiseOperations_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.isPowerOfTwoUsingBitwiseOperations(256));
    }

    @Test
    public void givenANonePowerOfTwoNum_whenUsingHighestOneBit_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.isPowerOfTwoUsingHighestOneBit(100));
    }

    @Test
    public void givenAPowerOfTwoNum_whenUsingHighestOneBit_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.isPowerOfTwoUsingHighestOneBit(256));
    }

}
