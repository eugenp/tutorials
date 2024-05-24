package com.baeldung.algorithms.permutation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountOccurrence {

    public static int[] countOccurrencesWithCounter(int[] elements) {
        int[] freq = new int[10];

        for (int i = 0; i < elements.length - 1; i++) {
            freq[elements[i]]++;
        }

        return freq;
    }

    public static Map<Integer, Integer> countOccurrencesWithMap(int[] elements) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int element : elements) {
            map.merge(element, 1, Integer::sum);
        }

        return map;
    }

    public static Map<String, Integer> countOccurrencesWithMap(String[] elements) {

        Map<String, Integer> map = new HashMap<>();

        for (String element : elements) {
            map.merge(element, 1, Integer::sum);
        }

        return map;
    }

    public static Map<Integer, Long> countOccurrencesWithStream(int[] elements) {

        return Arrays.stream(elements)
            .boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static Map<String, Long> countOccurrencesWithStream(String[] elements) {

        return Arrays.stream(elements)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    static int countOccurrencesWithBinarySearch(int[] arr, int n, int x) {
        int firstOccurrence = first(arr, n - 1, x);

        if (firstOccurrence == -1) {
            return -1;
        }

        int lastOccurrence = last(arr, n, firstOccurrence, n - 1, x);

        return lastOccurrence - firstOccurrence + 1;
    }

    /**
     * Loop over the lower and higher index of the array. Find the middle value.
     * If it is the one we are searching, and it is the first value in the array or the previous value is lower we have found the first occurrence.
     * Otherwise, if the value we are searching is greater than the middle we increase the lower index or decrease the higher index if it is lower
     *
     * @param arr input of integers
     * @param h length of the arr
     * @param x integer we are looking the occurrence for
     * @return first index occurrence of x or -1 if not found
     */
    static int first(int[] arr, int h, int x) {

        int l = 0;

        while (h >= l) {

            int mid = (l + h) / 2;

            if (arr[mid] == x && (mid == 0 || x > arr[mid - 1])) {
                return mid;
            } else if (x > arr[mid]) {
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Loop over the lower and higher index of the array. Find the middle value.
     * If it is the one we are searching, and it is the last value in the array or the next value is greater we have found the last occurrence.
     * Otherwise, if the value we are searching is lower than the middle we increase the lower index or decrease the higher index if it is greater
     *
     * @param arr input of integers
     * @param n length of the arr
     * @param l first occurrence of the integer we are searching
     * @param h length of the arr
     * @param x integer we are searching the occurrence for
     * @return last index occurrence of x or -1 if not found
     */
    static int last(int[] arr, int n, int l, int h, int x) {

        while (h >= l) {

            int mid = (l + h) / 2;

            if (arr[mid] == x && (mid == n - 1 || x < arr[mid + 1])) {
                return mid;
            } else if (x >= arr[mid]) {
                l = mid + 1;
            } else {
                h = mid - 1;
            }
        }
        return -1;
    }

}
