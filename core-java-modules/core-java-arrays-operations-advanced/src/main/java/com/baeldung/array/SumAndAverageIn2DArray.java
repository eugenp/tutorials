package com.baeldung.array;

import java.util.Arrays;

public class SumAndAverageIn2DArray {
    public static double findAverageWithoutUsingStream(int[][] array) {
        int sum = 0;
        int count = 0;
        for (int[] row : array) {
            for (int value : row) {
                sum += value;
                count++;
            }
        }
        return (double) sum / count;
    }

    public static double findAverageUsingStream(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).average().orElse(Double.NaN);
    }
}