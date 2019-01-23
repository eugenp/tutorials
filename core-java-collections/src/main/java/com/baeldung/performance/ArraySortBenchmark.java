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

        int[] array = new int[iterations];

        List<Integer> list = new ArrayList<>();

        @Setup(Level.Trial)
        public void setUp() {

            for (int i = 0; i < iterations; i++) {
                array[i] = i;
                list.add(i);
            }
        }

    }

    @Benchmark
    public int[] benchmarkArraysSort(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.array);
        return state.array;
    }

    @Benchmark
    public List<Integer> benchmarkCollectionsSort(ArraySortBenchmark.Initialize state) {
        Collections.sort(state.list);
        return state.list;
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
