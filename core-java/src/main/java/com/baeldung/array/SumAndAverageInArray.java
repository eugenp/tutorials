package com.baeldung.array;

import java.util.Arrays;

public class SumAndAverageInArray {

    public static int findSumWithoutUsingStream(int[] array) {
        int sum = 0;
        for (int index = 0; index < array.length; index++) {
            sum += array[index];
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
        return Arrays.stream(array).average().getAsDouble();
    }
}
