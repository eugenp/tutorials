package com.baeldung.maths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class BigIntegerDemoUnitTest {

    @Test
    public void whenBigIntegerCreatedFromConstructor_thenExpectedResult() {
        BigInteger biFromString = new BigInteger("1234567890987654321");
        BigInteger biFromByteArray = new BigInteger(
            new byte[] { 64, 64, 64, 64, 64, 64 });
        BigInteger biFromSignMagnitude = new BigInteger(-1,
            new byte[] { 64, 64, 64, 64, 64, 64 });

        assertEquals("1234567890987654321", biFromString.toString());
        assertEquals("70644700037184", biFromByteArray.toString());
        assertEquals("-70644700037184", biFromSignMagnitude.toString());
    }

    @Test
    public void whenLongConvertedToBigInteger_thenValueMatches() {
        BigInteger bi = BigInteger.valueOf(2305843009213693951L);

        assertEquals("2305843009213693951", bi.toString());
    }

    @Test
    public void givenBigIntegers_whentCompared_thenExpectedResult() {
        BigInteger i = new BigInteger("123456789012345678901234567890");
        BigInteger j = new BigInteger("123456789012345678901234567891");
        BigInteger k = new BigInteger("123456789012345678901234567892");

        assertTrue(i.compareTo(i) == 0);
        assertTrue(j.compareTo(i) > 0);
        assertTrue(j.compareTo(k) < 0);
    }

    @Test
    public void givenBigIntegers_whenPerformingArithmetic_thenExpectedResult() {
        BigInteger i = new BigInteger("4");
        BigInteger j = new BigInteger("2");

        BigInteger sum = i.add(j);
        BigInteger difference = i.subtract(j);
        BigInteger quotient = i.divide(j);
        BigInteger product = i.multiply(j);

        assertEquals(new BigInteger("6"), sum);
        assertEquals(new BigInteger("2"), difference);
        assertEquals(new BigInteger("2"), quotient);
        assertEquals(new BigInteger("8"), product);
    }

    @Test
    public void givenBigIntegers_whenPerformingBitOperations_thenExpectedResult() {
        BigInteger i = new BigInteger("17");
        BigInteger j = new BigInteger("7");

        BigInteger and = i.and(j);
        BigInteger or = i.or(j);
        BigInteger not = j.not();
        BigInteger xor = i.xor(j);
        BigInteger andNot = i.andNot(j);
        BigInteger shiftLeft = i.shiftLeft(1);
        BigInteger shiftRight = i.shiftRight(1);

        assertEquals(new BigInteger("1"), and);
        assertEquals(new BigInteger("23"), or);
        assertEquals(new BigInteger("-8"), not);
        assertEquals(new BigInteger("22"), xor);
        assertEquals(new BigInteger("16"), andNot);
        assertEquals(new BigInteger("34"), shiftLeft);
        assertEquals(new BigInteger("8"), shiftRight);
    }

    @Test
    public void givenBigIntegers_whenPerformingBitManipulations_thenExpectedResult() {
        BigInteger i = new BigInteger("1018");

        int bitCount = i.bitCount();
        int bitLength = i.bitLength();
        int getLowestSetBit = i.getLowestSetBit();
        boolean testBit3 = i.testBit(3);
        BigInteger setBit12 = i.setBit(12);
        BigInteger flipBit0 = i.flipBit(0);
        BigInteger clearBit3 = i.clearBit(3);

        assertEquals(8, bitCount);
        assertEquals(10, bitLength);
        assertEquals(1, getLowestSetBit);
        assertEquals(true, testBit3);
        assertEquals(new BigInteger("5114"), setBit12);
        assertEquals(new BigInteger("1019"), flipBit0);
        assertEquals(new BigInteger("1010"), clearBit3);
    }

    @Test
    public void givenBigIntegers_whenModularCalculation_thenExpectedResult() {
        BigInteger i = new BigInteger("31");
        BigInteger j = new BigInteger("24");
        BigInteger k = new BigInteger("16");

        BigInteger gcd = j.gcd(k);
        BigInteger multiplyAndmod = j.multiply(k)
            .mod(i);
        BigInteger modInverse = j.modInverse(i);
        BigInteger modPow = j.modPow(k, i);

        assertEquals(new BigInteger("8"), gcd);
        assertEquals(new BigInteger("12"), multiplyAndmod);
        assertEquals(new BigInteger("22"), modInverse);
        assertEquals(new BigInteger("7"), modPow);
    }

    @Test
    public void givenBigIntegers_whenPrimeOperations_thenExpectedResult() {
        BigInteger i = BigInteger.probablePrime(100, new Random());

        boolean isProbablePrime = i.isProbablePrime(1000);
        assertEquals(true, isProbablePrime);
    }
}
