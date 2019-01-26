package com.baeldung.array;

import java.util.Arrays;

public class SumAndAverageInArray {

    public static int findSumWithoutUsingStream(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    public static int findSumUsingStream(int[] array) {
        return Arrays.stream(array).sum();
    }

    public static int findSumUsingStream(Integer[] array) {
        return Arrays.stream(array).mapToInt(Integer::intValue).sum();
    }

    public static double findAverageWithoutUsingStream(int[] array) {
        int sum = findSumWithoutUsingStream(array);
        return (double) sum / array.length;
    }

    public static double findAverageUsingStream(int[] array) {
        return Arrays.stream(array).average().orElse(Double.NaN);
    }
}
