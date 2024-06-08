package com.baeldung.arbitrarylengthbinaryintegers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BinaryLiteralsUnitTest {

    @Test
    void givenTwoBinaryLiterals_whenAdding_thenResultIsCorrect() {
        int a = 0b110100101101010;
        int b = 0b1000;
        int expected = 0b110100101110010; // Result of a + b in binary
        assertEquals(expected, BinaryLiterals.add(a, b), "The addition of a and b should be correct");
    }

    @Test
    void givenTwoBinaryLiterals_whenSubtracting_thenResultIsCorrect() {
        int a = 0b110100101101010;
        int b = 0b1000;
        int expected = 0b110100101100010; // Result of a - b in binary
        assertEquals(expected, BinaryLiterals.subtract(a, b), "The subtraction of b from a should be correct");
    }

    @Test
    void givenTwoBinaryLiterals_whenMultiplying_thenResultIsCorrect() {
        int a = 0b110100101101010;
        int b = 0b1000;
        int expected = 0b110100101101010000; // Result of a * b in binary
        assertEquals(expected, BinaryLiterals.multiply(a, b), "The multiplication of a and b should be correct");
    }

    @Test
    void givenTwoBinaryLiterals_whenDividing_thenResultIsCorrect() {
        int a = 0b110100101101010;
        int b = 0b1000;
        int expected = 0b110100101101; // Result of a / b in binary
        assertEquals(expected, BinaryLiterals.divide(a, b), "The division of a by b should be correct");
    }

}
