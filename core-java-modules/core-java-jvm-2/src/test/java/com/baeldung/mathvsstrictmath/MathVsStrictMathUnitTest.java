package com.baeldung.mathvsstrictmath;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class MathVsStrictMathUnitTest {

    @Test
    public void givenSameInput_whenSameMethodsUsed_thenOutputDiffers() {
        compareMathPowWithStrictMathPow(12.937383454543545, 0.123345435353656556343434);
        compareMathPowWithStrictMathPow(0.1845936372497491, 0.01608930867480518);
        compareMathPowWithStrictMathPow(0.7281259501809544, 0.9414406865385655);

        compareMathExpWithStrictMathExp(1d);
        compareMathExpWithStrictMathExp(5.1d);
        compareMathExpWithStrictMathExp(15.1d);
    }

    private void compareMathPowWithStrictMathPow(double base, double power) {
        assertNotEquals(Math.pow(base, power), StrictMath.pow(base, power));
    }

    private void compareMathExpWithStrictMathExp(double expNo) {
        assertNotEquals(Math.exp(expNo), StrictMath.exp(expNo));
    }

}