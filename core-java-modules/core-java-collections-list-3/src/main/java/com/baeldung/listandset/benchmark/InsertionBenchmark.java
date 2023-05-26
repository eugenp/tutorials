package com.baeldung.listandset.benchmark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class InsertionBenchmark {

    public static void main(String[] args) {
        int numberOfElements = 100000;
        addBenchmark(numberOfElements);
    }

    public static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void addBenchmark(int numberOfElements) {
        List<Integer> arrayList = new ArrayList<>();
        Set<Integer> hashSet = new HashSet<>();

        long memoryBeforeList = getMemoryUsage();
        long startTimeList = System.nanoTime();

        for (int i = 0; i < numberOfElements; i++) {
            arrayList.add(i);
        }

        long endTimeList = System.nanoTime();
        long memoryAfterList = getMemoryUsage();

        long memoryBeforeSet = getMemoryUsage();
        long startTimeSet = System.nanoTime();

        for (int i = 0; i < numberOfElements; i++) {
            hashSet.add(i);
        }

        long endTimeSet = System.nanoTime();
        long memoryAfterSet = getMemoryUsage();

        long listTime = TimeUnit.NANOSECONDS.toMillis(endTimeList - startTimeList);
        long setTime = TimeUnit.NANOSECONDS.toMillis(endTimeSet - startTimeSet);
        long listMemory = memoryAfterList - memoryBeforeList;
        long setMemory = memoryAfterSet - memoryBeforeSet;

        System.out.println("List time: " + listTime + " ms");
        System.out.println("Set time: " + setTime + " ms");
        System.out.println("List memory: " + listMemory + " bytes");
        System.out.println("Set memory: " + setMemory + " bytes");
    }
}
