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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(batchSize = 100000, iterations = 10)
@Warmup(batchSize = 100000, iterations = 10)
public class ArraySortBenchmark {

    @State(Scope.Thread)
    public static class Initialize {
        Integer[] numbers = {5, 22, 10, 0};
        int[] primitives = {5, 22, 10, 0};
    }

    @Benchmark
    public Integer[] benchmarkArraysIntegerSort(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.numbers);
        return state.numbers;
    }

    @Benchmark
    public int[] benchmarkArraysIntSort(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.primitives);
        return state.primitives;
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
