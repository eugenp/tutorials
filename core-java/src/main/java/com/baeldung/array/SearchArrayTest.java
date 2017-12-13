package com.baeldung.array;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SearchArrayTest {

    @Benchmark
    public void searchArrayLoop() {

        int count = 1000;
        String[] strings = seedArray(count);
        for (int i = 0; i < count; i++) {
            searchLoop(strings, "T");
        }
    }

    @Benchmark
    public void searchArrayAllocNewList() {

        int count = 1000;
        String[] strings = seedArray(count);
        for (int i = 0; i < count; i++) {
            searchList(strings, "W");
        }

    }

    @Benchmark
    public void searchArrayAllocNewSet() {

        int count = 1000;
        String[] strings = seedArray(count);
        for (int i = 0; i < count; i++) {
            searchSet(strings, "S");
        }
    }


    @Benchmark
    public void searchArrayReuseList() {

        int count = 1000;
        String[] strings = seedArray(count);

        List<String> asList = Arrays.asList(strings);

        for (int i = 0; i < count; i++) {
            asList.contains("W");
        }
    }


    @Benchmark
    public void searchArrayReuseSet() {

        int count = 1000;
        String[] strings = seedArray(count);
        Set<String> asSet = new HashSet<>(Arrays.asList(strings));
        for (int i = 0; i < count; i++) {
            asSet.contains("S");
        }
    }


    @Benchmark
    public void searchArrayBinarySearch() {

        int count = 1000;
        String[] strings = seedArray(count);
        Arrays.sort(strings);

        long startTime = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Arrays.binarySearch(strings, "A");
        }
        long duration = System.nanoTime() - startTime;
        //System.out.println("Binary search:  " + duration / 10000);

    }

    private boolean searchList(String[] strings, String searchString) {
      return Arrays.asList(strings).contains(searchString);
  }

    private boolean searchSet(String[] strings, String searchString) {
        Set<String> set = new HashSet<>(Arrays.asList(strings));
        return set.contains(searchString);
    }

    private boolean searchLoop(String[] strings, String searchString) {
        for (String s : strings) {
            if (s.equals(searchString))
            return true;
        }
        return false;
    }

    private String[] seedArray(int length) {

        String[] strings = new String[length];
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            strings[i] = String.valueOf(random.nextInt());
        }
        return strings;
    }

}
