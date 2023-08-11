package com.baeldung.arraysums;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SumArraysUsingStreams {

    public static int[] sumOfTwoArrays(int[] arr1, int[] arr2) {
        IntStream stream1 = Arrays.stream(arr1);
        IntStream stream2 = Arrays.stream(arr2);
        IntStream stream3 = IntStream.zip(stream1, stream2);
        IntStream stream4 = stream3.map(pair -> pair[0] + pair[1]);
        int[] arr3 = stream4.toArray();
        return arr3;
    }
}
