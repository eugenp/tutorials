package com.baeldung.algorithms.binarygap;

import static com.baeldung.algorithms.binarygap.BinaryGap.calculateBinaryGap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BinaryGapUnitTest {

    @Test
    void givenNoOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(63);
        assertEquals(0, result);
    }

    @Test
    void givenTrailingZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(40);
        assertEquals(1, result);
    }

    @Test
    void givenSingleOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(9);
        assertEquals(2, result);
    }

    @Test
    void givenMultipleOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(145);
        assertEquals(3, result);
    }

}
