package com.baeldung.decimaltofraction;

import static com.baeldung.decimaltofraction.DecimalToFraction.convertDecimalToFractionUsingApacheCommonsMath;
import static com.baeldung.decimaltofraction.DecimalToFraction.convertDecimalToFractionUsingGCD;
import static com.baeldung.decimaltofraction.DecimalToFraction.convertDecimalToFractionUsingGCDRepeating;
import static com.baeldung.decimaltofraction.DecimalToFraction.convertDecimalToFractionUsingMultiplyingWithPowerOf10;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class DecimalToFractionUnitTest {

    @Test
    void givenADecimal_whenUsingMultiplyingWithPowerOf10_thenReturnFraction() {
        assertEquals("5/10", convertDecimalToFractionUsingMultiplyingWithPowerOf10(0.5));
        assertEquals("1/10", convertDecimalToFractionUsingMultiplyingWithPowerOf10(0.1));
        assertEquals("6/10", convertDecimalToFractionUsingMultiplyingWithPowerOf10(0.6));
        assertEquals("85/100", convertDecimalToFractionUsingMultiplyingWithPowerOf10(0.85));
        assertEquals("125/100", convertDecimalToFractionUsingMultiplyingWithPowerOf10(1.25));
        assertEquals("1333333333/1000000000", convertDecimalToFractionUsingMultiplyingWithPowerOf10(1.333333333));
    }

    @Test
    void givenADecimal_whenUsingGCD_thenReturnFraction() {
        assertEquals("1/2", convertDecimalToFractionUsingGCD(0.5));
        assertEquals("1/10", convertDecimalToFractionUsingGCD(0.1));
        assertEquals("3/5", convertDecimalToFractionUsingGCD(0.6));
        assertEquals("17/20", convertDecimalToFractionUsingGCD(0.85));
        assertEquals("5/4", convertDecimalToFractionUsingGCD(1.25));
        assertEquals("1333333333/1000000000", convertDecimalToFractionUsingGCD(1.333333333));
    }

    @Test
    void givenADecimal_whenUsingApacheCommonsMath_thenReturnFraction() {
        assertEquals("1 / 2", convertDecimalToFractionUsingApacheCommonsMath(0.5));
        assertEquals("1 / 10", convertDecimalToFractionUsingApacheCommonsMath(0.1));
        assertEquals("3 / 5", convertDecimalToFractionUsingApacheCommonsMath(0.6));
        assertEquals("17 / 20", convertDecimalToFractionUsingApacheCommonsMath(0.85));
        assertEquals("5 / 4", convertDecimalToFractionUsingApacheCommonsMath(1.25));
        assertEquals("4 / 3", convertDecimalToFractionUsingApacheCommonsMath(1.333333333));
    }

    @Test
    void givenADecimal_whenUsingGCDRepeating_thenReturnFraction() {
        assertEquals("1/2", convertDecimalToFractionUsingGCDRepeating(0.5));
        assertEquals("17/20", convertDecimalToFractionUsingGCDRepeating(0.85));
        assertEquals("5/4", convertDecimalToFractionUsingGCDRepeating(1.25));
        assertEquals("4/3", convertDecimalToFractionUsingGCDRepeating(1.333333333));
        assertEquals("7/9", convertDecimalToFractionUsingGCDRepeating(0.777777));
    }
}
