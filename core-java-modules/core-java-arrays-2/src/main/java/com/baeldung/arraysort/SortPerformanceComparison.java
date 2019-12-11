package com.baeldung.arraysort;

import java.util.Arrays;
import java.util.Random;

public class SortPerformanceComparison {

    public static void main(String[] args) {
        int[] sizeOfArrays = { 1000, 10000, 100000, 1000000, 10000000 };
        for (int arraySize : sizeOfArrays) {
            System.out.println("Test for array size = " + arraySize);

            int[] arr = new int[arraySize];
            Random random = new Random();

            for (int i = 0; i < arraySize; i++)
                arr[i] = random.nextInt(arraySize) + random.nextInt(arraySize);

            int[] sequentialDataSet = Arrays.copyOf(arr, arr.length);
            int[] parallelDataSet = Arrays.copyOf(arr, arr.length);

            long begin = System.currentTimeMillis();
            Arrays.sort(sequentialDataSet);
            long end = System.currentTimeMillis();

            System.out.println("Arrays.sort(): " + (end - begin));

            begin = System.currentTimeMillis();
            Arrays.parallelSort(parallelDataSet);
            end = System.currentTimeMillis();

            System.out.println("Arrays.parallelSort(): " + (end - begin));
            System.out.println("*************************");
        }
    }
}
