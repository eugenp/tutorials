package com.baeldung.biginteger;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BigIntegerUnitTest {

    @Test
    void givenPositiveAndNegativeAndZeroBigInteger_whenGetSigNumValue_shouldReturnOneAndMinusOneAndZero() {
        assertEquals(1, BigInteger.TEN.signum());
        assertEquals(-1, BigInteger.TEN.negate().signum());
        assertEquals(0, BigInteger.ZERO.signum());
    }

    @Test
    void givenByteArrays_whenCreateBigInteger_shouldTranslateToTwosComplementBinary() {
        assertEquals(new BigInteger("1"), new BigInteger(new byte[]{0b1}));
        assertEquals(new BigInteger("2"), new BigInteger(new byte[]{0b10}));
        assertEquals(new BigInteger("4"), new BigInteger(new byte[]{0b100}));
    }

    @Test
    void givenSingleByte_whenCreateBigIntegerAndDifferentSigNum_thenOppositeValues() {
        byte[] bytes = { (byte) -128 }; // 0b1000_0000

        BigInteger positive = new BigInteger(1, bytes);
        BigInteger negative = new BigInteger(-1, bytes);

        assertEquals(new BigInteger("128"), positive);
        assertEquals("10000000", positive.toString(2));

        assertEquals(new BigInteger("-128"), negative);
        assertEquals("-10000000", negative.toString(2));
    }

    @Test
    void givenZeroBigInteger_whenCheckMagnitude_thenIsEmpty() {
        assertEquals(0, BigInteger.ZERO.bitCount());
        assertEquals(BigInteger.ZERO, new BigInteger(0, new byte[]{}));
    }

    @Test
    void given63rdBitSet_whenCreateBigInteger_thenIsLargerThanLongByOne() {
        // first
        BigInteger bi1 = BigInteger.ZERO.setBit(63);
        String str1 = bi1.toString(2);

        // second
        byte[] bytes = ByteBuffer.allocate(Long.BYTES).putLong(Long.MIN_VALUE).array();
        BigInteger bi2 = new BigInteger(1, bytes);
        String str2 = bi2.toString(2);

        largerThanLongAssertionSet(bi1, str1);

        assertEquals(bi1, bi2);

        largerThanLongAssertionSet(bi2, str2);

    }

    private static void largerThanLongAssertionSet(BigInteger bi, String str)
    {
        assertEquals(64, bi.bitLength());
        assertEquals(1, bi.signum());
        assertEquals("9223372036854775808", bi.toString());
        assertEquals(BigInteger.ONE,  bi.subtract(BigInteger.valueOf(Long.MAX_VALUE)));

        assertEquals(64, str.length());
        assertTrue(str.matches("^10{63}$")); // 1000 0000
    }
}
