package com.baeldung.math.percentdiffbetweentwonumberstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.baeldung.math.percentdiffbetweentwonumbers.PercentDiffBetweenTwoNumbers;
import org.junit.jupiter.api.Test;
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
  void whenV1AndV2AreEqual_thenPercentageDifferenceIsZero() {
    double v1 = 50.0;
    double v2 = 50.0;
    double expected = 0.0;
    double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
    assertEquals(
        expected, result, "Percentage difference should be zero when both values are equal.");
  }

  @Test
  void whenV1IsGreaterThanV2_thenCalculateCorrectPercentageDifference() {
    double v1 = 70.0;
    double v2 = 50.0;
    double expected = 33.33;
    double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
    assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
  }

  @Test
  void whenV2IsGreaterThanV1_thenCalculateCorrectPercentageDifference() {
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
    assertEquals(
        expected,
        result,
        "Percentage difference should be correctly calculated when one value is zero.");
  }

  @Test
  void whenAverageIsZero_thenThrowIllegalArgumentException() {
    double v1 = -50.0;
    double v2 = 50.0;
    assertThrows(
        IllegalArgumentException.class,
        () -> PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2),
        "Should throw IllegalArgumentException when the average of V1 and V2 is zero.");
  }

  @Test
  void whenV1AndV2AreSwapped_thenPercentageDifferenceIsSame() {
    double v1 = 70.0;
    double v2 = 50.0;
    double expected = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v1, v2);
    double result = PercentDiffBetweenTwoNumbers.calculatePercentageDifference(v2, v1);
    assertEquals(
        expected,
        result,
        0.01,
        "Percentage difference should be the same when V1 and V2 are swapped.");
  }
}
