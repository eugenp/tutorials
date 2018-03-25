package com.baeldung.array;

import java.util.Arrays;
import java.util.OptionalDouble;

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

    public static double findAverageWithoutUsingStream(int[] array) {
        int sum = findSumWithoutUsingStream(array);
        return (double) sum / array.length;
    }

    public static double findAverageUsingStream(int[] array) {
        if (array.length == 0) {
            return 0;
        } else {
            return Arrays.stream(array).average().getAsDouble();
        }
    }
}
