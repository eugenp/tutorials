package com.baeldung.math.percentagedifferecebetweentwonumbers;

public class PercentageDifferenceBetweenTwoNumbers {
  public static double calculatePercentageDifference(double V1, double V2) {
    double average = (V1 + V2) / 2;
    if (average == 0) {
      throw new IllegalArgumentException("The average of V1 and V2 cannot be zero.");
    }
    return Math.abs((V1 - V2) / average) * 100;
  }
}
