package com.baeldung.algorithms.binarygap;

import static com.baeldung.algorithms.binarygap.BinaryGap.calculateBinaryGap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinaryGapUnitTest {

    @Test public void givenNoOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(63);
        assertEquals(0, result);
    }

    @Test public void givenTrailingZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(40);
        assertEquals(1, result);
    }

    @Test public void givenSingleOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(9);
        assertEquals(2, result);
    }

    @Test public void givenMultipleOccurrenceOfBoundedZeros_whenCalculateBinaryGap_thenOutputCorrectResult() {

        int result = calculateBinaryGap(145);
        assertEquals(3, result);
    }

}
