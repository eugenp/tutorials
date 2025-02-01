package com.baeldung.math.percentdiffbetweentwonumberstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.baeldung.math.percentdiffbetweentwonumbers.PercentDiffBetweenTwoNumbers;

class PercentDiffBetweenTwoNumbersUnitTest {

    @Test
    void whenCalculatePercentageDifferenceBetweenTwoNumbers_thenCorrectResult() {
        double v1 = 50.0;
        double v2 = 70.0;
        double expected = 33.33;
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
    }

    @Test
    void whenv1Andv2AreEqual_thenPercentageDifferenceIsZero() {
        double v1 = 50.0;
        double v2 = 50.0;
        double expected = 0.0;
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        assertEquals(expected, result, "Percentage difference should be zero when both values are equal.");
    }

    @Test
    void whenv1IsGreaterThanv2_thenCalculateCorrectPercentageDifference() {
        double v1 = 70.0;
        double v2 = 50.0;
        double expected = 33.33;
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
    }

    @Test
    void whenv2IsGreaterThanv1_thenCalculateCorrectPercentageDifference() {
        double v1 = 50.0;
        double v2 = 70.0;
        double expected = 33.33;
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
    }

    @Test
    void whenOneValueIsZero_thenCalculateCorrectPercentageDifference() {
        double v1 = 0.0;
        double v2 = 50.0;
        double expected = 200.0;
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        assertEquals(expected, result, "Percentage difference should be correctly calculated when one value is zero.");
    }

    @Test
    void whenAverageIsZero_thenThrowIllegalArgumentException() {
        double v1 = -50.0;
        double v2 = 50.0;
        assertThrows(IllegalArgumentException.class, () -> PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2),
            "Should throw IllegalArgumentException when the average of v1 and v2 is zero.");
    }

    @Test
    void whenv1Andv2AreSwapped_thenPercentageDifferenceIsSame() {
        double v1 = 70.0;
        double v2 = 50.0;
        double expected = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
        double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v2, v1);
        assertEquals(expected, result, 0.01, "Percentage difference should be the same when v1 and v2 are swapped.");
    }
}
