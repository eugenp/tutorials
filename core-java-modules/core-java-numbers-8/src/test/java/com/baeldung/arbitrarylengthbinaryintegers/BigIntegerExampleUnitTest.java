package com.baeldung.arbitrarylengthbinaryintegers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class BigIntegerExampleUnitTest {

    @Test
    void givenTwoBigIntegers_whenAdding_thenResultIsCorrect() {
        BigInteger a = new BigInteger("1101001011010101010101010101010101010101010101010101010101010101010", 2);
        BigInteger b = new BigInteger("-10000", 2);
        BigInteger expected = new BigInteger("1101001011010101010101010101010101010101010101010101010101010011010", 2);
        assertEquals(expected, BigIntegerExample.add(a, b), "The addition of a and b should be correct");
    }

    @Test
    void givenTwoBigIntegers_whenSubtracting_thenResultIsCorrect() {
        BigInteger a = new BigInteger("1101001011010101010101010101010101010101010101010101010101010101010", 2);
        BigInteger b = new BigInteger("-10000", 2);
        BigInteger expected = new BigInteger("1101001011010101010101010101010101010101010101010101010101010111010", 2);
        assertEquals(expected, BigIntegerExample.subtract(a, b), "The subtraction of b from a should be correct");
    }

    @Test
    void givenTwoBigIntegers_whenMultiplying_thenResultIsCorrect() {
        BigInteger a = new BigInteger("1101001011010101010101010101010101010101010101010101010101010101010", 2);
        BigInteger b = new BigInteger("-10000", 2);
        BigInteger expected = new BigInteger("-11010010110101010101010101010101010101010101010101010101010101010100000", 2);
        assertEquals(expected, BigIntegerExample.multiply(a, b), "The multiplication of a and b should be correct");
    }

    @Test
    void givenTwoBigIntegers_whenDividing_thenResultIsCorrect() {
        BigInteger a = new BigInteger("1101001011010101010101010101010101010101010101010101010101010101010", 2);
        BigInteger b = new BigInteger("-10000", 2);
        BigInteger expected = new BigInteger("-110100101101010101010101010101010101010101010101010101010101010", 2);
        assertEquals(expected, BigIntegerExample.divide(a, b), "The division of a by b should be correct");
    }

}