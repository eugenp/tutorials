package com.baeldung.onescomplement;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class OnesComplementUnitTest {

    @Test
    void givenAPositiveNumber_whenCalculateOnesComplementUsingBitwiseNot_thenReturnOneComplementValue() {
        int onesComplement = OnesComplement.calculateOnesComplementUsingBitwiseNot(10);

        assertEquals(-11, onesComplement);
        assertEquals("11111111111111111111111111110101", Integer.toBinaryString(onesComplement));
    }

    @Test
    void givenANegativeNumber_whenCalculateOnesComplementUsingBitwiseNot_thenReturnOneComplementValue() {
        int onesComplement = OnesComplement.calculateOnesComplementUsingBitwiseNot(-10);

        assertEquals(9, onesComplement);
        assertEquals("1001", Integer.toBinaryString(onesComplement));
    }

    @Test
    void givenAPositiveNumber_whenCalculateOnesComplementUsingBigInteger_thenReturnOneComplementValue() {
        BigInteger onesComplement = OnesComplement.calculateOnesComplementUsingBigInteger(BigInteger.valueOf(10));

        assertEquals(BigInteger.valueOf(-11), onesComplement);
        assertEquals("11111111111111111111111111110101", Integer.toBinaryString(onesComplement.intValue()));
    }

    @Test
    void givenANegativeNumber_whenCalculateOnesComplementUsingBigInteger_thenReturnOneComplementValue() {
        BigInteger onesComplement = OnesComplement.calculateOnesComplementUsingBigInteger(BigInteger.valueOf(-10));

        assertEquals(BigInteger.valueOf(9), onesComplement);
        assertEquals("1001", Integer.toBinaryString(onesComplement.intValue()));
    }

    @Test
    void givenAPositiveNumber_whenCalculateOnesComplementUsingXOROperator_thenReturnOneComplementValue() {
        int onesComplement = OnesComplement.calculateOnesComplementUsingXOROperator(10, 8);

        assertEquals(245, onesComplement);
        assertEquals("11110101", Integer.toBinaryString(onesComplement));
    }

    @Test
    void givenANegativeNumber_whenCalculateOnesComplementUsingXOROperator_thenReturnOneComplementValue() {
        int onesComplement = OnesComplement.calculateOnesComplementUsingXOROperator(-10, 8);

        assertEquals(9, onesComplement);
        assertEquals("1001", Integer.toBinaryString(onesComplement));
    }
}
