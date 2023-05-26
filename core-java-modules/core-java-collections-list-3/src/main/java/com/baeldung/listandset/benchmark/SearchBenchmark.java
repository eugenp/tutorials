package com.baeldung.listandset.benchmark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SearchBenchmark {

    public static void main(String[] args) {
        int numberOfElements = 100000;
        benchmarkSearch(numberOfElements);
    }

    public static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void benchmarkSearch(int numberOfElements) {
        List<Integer> arrayList = new ArrayList<>();
        Set<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < numberOfElements; i++) {
            arrayList.add(i);
            hashSet.add(i);
        }

        long memoryBeforeListSearch = getMemoryUsage();
        long startTimeListSearch = System.nanoTime();

        for (int i = 0; i < numberOfElements; i++) {
            arrayList.contains(i);
        }

        long endTimeListSearch = System.nanoTime();
        long memoryAfterListSearch = getMemoryUsage();

        long memoryBeforeSetSearch = getMemoryUsage();
        long startTimeSetSearch = System.nanoTime();

        for (int i = 0; i < numberOfElements; i++) {
            hashSet.contains(i);
        }

        long endTimeSetSearch = System.nanoTime();
        long memoryAfterSetSearch = getMemoryUsage();

        long listSearchTime = TimeUnit.NANOSECONDS.toMillis(endTimeListSearch - startTimeListSearch);
        long setSearchTime = TimeUnit.NANOSECONDS.toMillis(endTimeSetSearch - startTimeSetSearch);
        long listSearchMemory = memoryAfterListSearch - memoryBeforeListSearch;
        long setSearchMemory = memoryAfterSetSearch - memoryBeforeSetSearch;

        System.out.println("List search time: " + listSearchTime + " ms");
        System.out.println("Set search time: " + setSearchTime + " ms");
        System.out.println("List search memory: " + listSearchMemory + " bytes");
        System.out.println("Set search memory: " + setSearchMemory + " bytes");
    }

}
