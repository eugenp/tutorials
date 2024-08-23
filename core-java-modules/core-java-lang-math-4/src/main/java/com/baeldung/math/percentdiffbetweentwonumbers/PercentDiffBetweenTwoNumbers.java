package com.baeldung.math.percentdiffbetweentwonumbers;

public class PercentDiffBetweenTwoNumbers {

    public static double calculatePercentageDifference(double v1, double v2) {
        double average = (v1 + v2) / 2;
        if (average == 0) {
            throw new IllegalArgumentException("The average of v1 and v2 cannot be zero.");
        }
        return Math.abs((v1 - v2) / average) * 100;
    }
}
