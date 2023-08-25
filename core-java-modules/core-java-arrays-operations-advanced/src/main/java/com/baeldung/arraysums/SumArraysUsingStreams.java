package com.baeldung.arraysums;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SumArraysUsingStreams {

    public static int[] sumOfTwoArrays(int[] arr1, int[] arr2) {
        IntStream range = IntStream.range(0, Math.min(arr1.length, arr2.length));
        IntStream stream3 = range.map(i -> arr1[i] + arr2[i]);
        int[] arr3 = stream3.toArray();
        return arr3;
    }
}
