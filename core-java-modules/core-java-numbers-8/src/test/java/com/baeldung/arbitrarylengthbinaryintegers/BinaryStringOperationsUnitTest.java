package com.baeldung.arbitrarylengthbinaryintegers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BinaryStringOperationsUnitTest {

    @Test
    void givenTwoBinaryStrings_whenAdding_thenResultIsCorrect() {
        String a = "1101001011010101010101010101010101010101010101010101010101010101010";
        String b = "-10000";
        String expected = "1101001011010101010101010101010101010101010101010101010101010011010"; // Expected result of a + b
        assertEquals(expected, BinaryStringOperations.add(a, b), "The addition of a and b should be correct");
    }

    @Test
    void givenTwoBinaryStrings_whenSubtracting_thenResultIsCorrect() {
        String a = "1101001011010101010101010101010101010101010101010101010101010101010";
        String b = "-10000";
        String expected = "1101001011010101010101010101010101010101010101010101010101010111010"; // Expected result of a - b
        assertEquals(expected, BinaryStringOperations.subtract(a, b), "The subtraction of b from a should be correct");
    }

    @Test
    void givenTwoBinaryStrings_whenMultiplying_thenResultIsCorrect() {
        String a = "1101001011010101010101010101010101010101010101010101010101010101010";
        String b = "-10000";
        String expected = "-11010010110101010101010101010101010101010101010101010101010101010100000"; // Expected result of a * b
        assertEquals(expected, BinaryStringOperations.multiply(a, b), "The multiplication of a and b should be correct");
    }

    @Test
    void givenTwoBinaryStrings_whenDividing_thenResultIsCorrect() {
        String a = "1101001011010101010101010101010101010101010101010101010101010101010";
        String b = "-10000";
        String expected = "-110100101101010101010101010101010101010101010101010101010101010"; // Expected result of a / b
        assertEquals(expected, BinaryStringOperations.divide(a, b), "The division of a by b should be correct");
    }
    
}
