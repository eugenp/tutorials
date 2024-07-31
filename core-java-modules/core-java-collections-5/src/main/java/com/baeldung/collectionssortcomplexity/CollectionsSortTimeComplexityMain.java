package com.baeldung.collectionssortcomplexity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsSortTimeComplexityMain {
    // O(n log n) Time Complexity Example
    public static void worstAndAverageCasesTimeComplexity() {
        Integer[] sortedArray = {20, 21, 22, 23, 24, 25, 26, 17, 28, 29, 30, 31, 18, 19, 32, 33, 34, 27, 35};
        List<Integer> list = Arrays.asList(sortedArray);
        Collections.shuffle(list);
        long startTime = System.nanoTime();
        Collections.sort(list);
        long endTime = System.nanoTime();
        System.out.println("Execution Time for O(n log n): " + (endTime - startTime) + " nanoseconds");
    }

    // O(n) Time Complexity Example
    public static void bestCaseTimeComplexity() {
        Integer[] sortedArray = {19, 22, 19, 22, 24, 25, 17, 11, 22, 23, 28, 23, 0, 1, 12, 9, 13, 27, 15};
        List<Integer> list = Arrays.asList(sortedArray);
        long startTime = System.nanoTime();
        Collections.sort(list);
        long endTime = System.nanoTime();
        System.out.println("Execution Time for O(n): " + (endTime - startTime) + " nanoseconds");
    }

    public static void main(String[] args) {
        worstAndAverageCasesTimeComplexity();
        bestCaseTimeComplexity();
    }
}
