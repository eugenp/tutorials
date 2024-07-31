package com.baeldung.array;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SearchArrayBenchmark {

    @State(Scope.Benchmark)
    public static class SearchData {
        static int count = 1000;
        static String[] strings = seedArray(1000);
    }


    @Benchmark
    public void searchArrayLoop() {
        for (int i = 0; i < SearchData.count; i++) {
            searchLoop(SearchData.strings, "T");
        }
    }

    @Benchmark
    public void searchArrayAllocNewList() {
        for (int i = 0; i < SearchData.count; i++) {
            searchList(SearchData.strings, "T");
        }

    }

    @Benchmark
    public void searchArrayAllocNewSet() {
        for (int i = 0; i < SearchData.count; i++) {
            searchSet(SearchData.strings, "T");
        }
    }


    @Benchmark
    public void searchArrayReuseList() {
        List<String> asList = Arrays.asList(SearchData.strings);
        for (int i = 0; i < SearchData.count; i++) {
            asList.contains("T");
        }
    }


    @Benchmark
    public void searchArrayReuseSet() {
        Set<String> asSet = new HashSet<>(Arrays.asList(SearchData.strings));
        for (int i = 0; i < SearchData.count; i++) {
            asSet.contains("T");
        }
    }


    @Benchmark
    public void searchArrayBinarySearch() {
        Arrays.sort(SearchData.strings);
        for (int i = 0; i < SearchData.count; i++) {
            Arrays.binarySearch(SearchData.strings, "T");
        }
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

    private static String[] seedArray(int length) {
        String[] strings = new String[length];
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            strings[i] = String.valueOf(random.nextInt());
        }
        return strings;
    }

}
