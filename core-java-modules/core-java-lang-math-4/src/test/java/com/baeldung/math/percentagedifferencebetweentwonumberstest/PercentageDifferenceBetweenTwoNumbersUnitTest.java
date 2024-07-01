package com.baeldung.math.percentagedifferencebetweentwonumberstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.baeldung.math.percentagedifferecebetweentwonumbers.PercentageDifferenceBetweenTwoNumbers;
import org.junit.jupiter.api.Test;

class PercentageDifferenceBetweenTwoNumbersUnitTest {

  @Test
  void whenCalculatePercentageDifferenceBetweenTwoNumbers_thenCorrectResult() {
    double V1 = 50.0;
    double V2 = 70.0;
    double expected = 33.33;
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
  }

  @Test
  void whenV1AndV2AreEqual_thenPercentageDifferenceIsZero() {
    double V1 = 50.0;
    double V2 = 50.0;
    double expected = 0.0;
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    assertEquals(
        expected, result, "Percentage difference should be zero when both values are equal.");
  }

  @Test
  void whenV1IsGreaterThanV2_thenCalculateCorrectPercentageDifference() {
    double V1 = 70.0;
    double V2 = 50.0;
    double expected = 33.33;
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
  }

  @Test
  void whenV2IsGreaterThanV1_thenCalculateCorrectPercentageDifference() {
    double V1 = 50.0;
    double V2 = 70.0;
    double expected = 33.33;
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    assertEquals(expected, result, 0.01, "Percentage difference should be correctly calculated.");
  }

  @Test
  void whenOneValueIsZero_thenCalculateCorrectPercentageDifference() {
    double V1 = 0.0;
    double V2 = 50.0;
    double expected = 200.0;
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    assertEquals(
        expected,
        result,
        "Percentage difference should be correctly calculated when one value is zero.");
  }

  @Test
  void whenAverageIsZero_thenThrowIllegalArgumentException() {
    double V1 = -50.0;
    double V2 = 50.0;
    assertThrows(
        IllegalArgumentException.class,
        () -> PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2),
        "Should throw IllegalArgumentException when the average of V1 and V2 is zero.");
  }

  @Test
  void whenV1AndV2AreSwapped_thenPercentageDifferenceIsSame() {
    double V1 = 70.0;
    double V2 = 50.0;
    double expected = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V1, V2);
    double result = PercentageDifferenceBetweenTwoNumbers.calculatePercentageDifference(V2, V1);
    assertEquals(
        expected,
        result,
        0.01,
        "Percentage difference should be the same when V1 and V2 are swapped.");
  }
}
