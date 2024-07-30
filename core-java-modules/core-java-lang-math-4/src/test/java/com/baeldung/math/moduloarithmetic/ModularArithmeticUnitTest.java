package com.baeldung.math.moduloarithmetic;

import static com.baeldung.math.moduloarithmetic.ModularArithmetic.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ModularArithmeticUnitTest {

    private static final int MOD = 1000000007;

    @Test
    public void givenTwoNumbers_whenModSum_thenReturnCorrectModularSum() {
        assertEquals(1000000006, modSum(500000003, 500000003));
        assertEquals(1, modSum(1000000006, 2));
        assertEquals(999999999, modSum(999999999, 0));
        assertEquals(1000000005, modSum(1000000006, 1000000006));
    }

    @Test
    public void givenTwoNumbers_whenModSubtract_thenReturnCorrectModularDifference() {
        assertEquals(0, modSubtract(500000003, 500000003));
        assertEquals(1000000005, modSubtract(1, 3));
        assertEquals(999999999, modSubtract(999999999, 0));
    }

    @Test
    public void givenTwoNumbers_whenModMultiply_thenReturnCorrectModularMultiplier() {
        assertEquals(1, modMultiply(1000000006, 1000000006));
        assertEquals(0, modMultiply(999999999, 0));
        assertEquals(1000000006, modMultiply(500000003, 2));
        assertEquals(250000002, modMultiply(500000003, 500000003));
    }

    @Test
    public void givenBaseAndExponent_whenModPower_thenReturnCorrectResult() {
        assertEquals(16, modPower(2, 4));
        assertEquals(1, modPower(2, 0));
        assertEquals(1000000006, modPower(1000000006, 1));
        assertEquals(1000000006, modPower(500000003, 500000003));
        assertEquals(500000004, modPower(500000004, 500000004));
        assertEquals(250000004, modPower(500000005, 500000005));
    }

    @Test
    public void givenNumber_whenModInverse_thenReturnCorrectModularMultiplicativeInverse() {
        assertEquals(500000004, modInverse(2));
        assertEquals(1, modInverse(1));
        assertEquals(1000000006, modInverse(1000000006));
        assertEquals(1000000005, modInverse(500000003));
    }

    @Test
    public void givenDividendAndDivisor_whenModDivide_thenReturnCorrectResult() {
        assertEquals(500000004, modDivide(1, 2));
        assertEquals(2, modDivide(4, 2));
        assertEquals(1000000006, modDivide(1000000006, 1));
    }
}
