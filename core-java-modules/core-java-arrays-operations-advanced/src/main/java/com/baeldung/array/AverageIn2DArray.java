package com.baeldung.array;

import java.util.Arrays;
import java.util.OptionalDouble;

public class AverageIn2DArray {
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

    public static OptionalDouble findAverageUsingStream(int[][] array) {
        return Arrays.stream(array)
                .flatMapToInt(Arrays::stream)
                .average();
    }
}