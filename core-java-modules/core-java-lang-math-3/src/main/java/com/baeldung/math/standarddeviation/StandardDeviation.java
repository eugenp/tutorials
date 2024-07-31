package com.baeldung.math.standarddeviation;

import java.util.Arrays;

public class StandardDeviation {

    public static double calculateStandardDeviation(double[] array) {

        // get the sum of array
        double sum = 0.0;
        for (double i : array) {
            sum += i;
        }

        // get the mean of array
        int length = array.length;
        double mean = sum / length;

        // calculate the standard deviation
        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    public static void main(String[] args) {
        double[] array = {25, 5, 45, 68, 61, 46, 24, 95};
        System.out.println("List of elements: " + Arrays.toString(array));

        double standardDeviation = calculateStandardDeviation(array);
        System.out.format("Standard Deviation = %.6f", standardDeviation);
    }
}
