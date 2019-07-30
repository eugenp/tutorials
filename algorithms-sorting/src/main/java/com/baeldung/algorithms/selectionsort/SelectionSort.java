package com.baeldung.algorithms.selectionsort;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectionSort {

    /*public static void main(final String args[]) {
        int[] a = { 5, 4, 1, 6, 2 };
    
        System.out.println("Unsorted array");
        System.out.println(IntStream.of(a)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",")));
    
        a = sortAscending(a);
        System.out.println("Ascending Sorted array");
        System.out.println(IntStream.of(a)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",")));
    
        a = sortDescending(a);
        System.out.println("Descending Sorted array");
        System.out.println(IntStream.of(a)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",")));
    }*/

    public static void sortAscending(final int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minElementIndex] > arr[j]) {
                    minElementIndex = j;
                }
            }

            if (minElementIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minElementIndex];
                arr[minElementIndex] = temp;
            }
            System.out.println("Iteration No. " + (i + 1));
            System.out.println(IntStream.of(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",")));

        }
    }

    public static void sortDescending(final int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int maxElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[maxElementIndex] < arr[j]) {
                    maxElementIndex = j;
                }
            }

            if (maxElementIndex != i) {
                int temp = arr[i];
                arr[i] = arr[maxElementIndex];
                arr[maxElementIndex] = temp;
            }
            System.out.println("Iteration No. " + (i + 1));
            System.out.println(IntStream.of(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(",")));

        }
    }
}