package com.baeldung.poweroftwo;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class CheckPowerOfTwoUnitTest {

    @Test
    public void givenANonePowerOfTwoNum_whenUsingLogarithm_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.usingLogarithm(100));
    }

    @Test
    public void givenAPowerOfTwoNum_whenUsingLogarithm_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.usingLogarithm(256));
    }

    @Test
    public void givenANonePowerOfTwoNum_whenUsingBitwiseOperations_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.usingBitwiseOperations(100));
    }


    @Test
    public void givenAPowerOfTwoNum_whenUsingBitwiseOperations_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.usingBitwiseOperations(256));
    }

    @Test
    public void givenANonePowerOfTwoNum_whenUsingHighestOneBit_thenReturnFalse() {
        assertFalse(CheckPowerOfTwo.usingHighestOneBit(100));
    }


    @Test
    public void givenAPowerOfTwoNum_whenUsingHighestOneBit_thenReturnTrue() {
        assertTrue(CheckPowerOfTwo.usingHighestOneBit(256));
    }

}
