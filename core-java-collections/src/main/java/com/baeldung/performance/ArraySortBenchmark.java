package com.baeldung.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MINUTES)
@Measurement(batchSize = 100000, iterations = 10)
@Warmup(batchSize = 100000, iterations = 10)
public class ArraySortBenchmark {

    @State(Scope.Thread)
    public static class Initialize {

        int iterations = 1000;

        String[] array = new String[iterations];
        List<String> list = new ArrayList<>();

        int[] intArray = new int[iterations];
        List<Integer> integerList = new ArrayList<>();

        @Setup(Level.Trial)
        public void setUp() {
            for (int i = 0; i < iterations; i++) {
                array[i] = i + "";
                list.add(i + "");

                intArray[i] = i;
                integerList.add(i);
            }
        }
    }

    @Benchmark
    public String[] benchmarkArraysSort(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.array);
        return state.array;
    }

    @Benchmark
    public List<String> benchmarkCollectionsSort(ArraySortBenchmark.Initialize state) {
        Collections.sort(state.list);
        return state.list;
    }

    @Benchmark
    public int[] benchmarkArraysSortInt(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.intArray);
        return state.intArray;
    }

    @Benchmark
    public List<Integer> benchmarkCollectionsSortInteger(ArraySortBenchmark.Initialize state) {
        Collections.sort(state.integerList);
        return state.integerList;
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(ArraySortBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
