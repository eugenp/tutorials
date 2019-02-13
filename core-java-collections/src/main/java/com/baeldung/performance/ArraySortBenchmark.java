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

        String[] words = {"Java", "Baeldung", "Tutorial"};
        List<String> wordList = new ArrayList<>();

        @Setup(Level.Trial)
        public void setUp() {
            wordList.add("Java");
            wordList.add("Baeldung");
            wordList.add("Tutorial");
        }
    }

    @Benchmark
    public String[] benchmarkArraysSort(ArraySortBenchmark.Initialize state) {
        Arrays.sort(state.words);
        return state.words;
    }

    @Benchmark
    public List<String> benchmarkCollectionsSort(ArraySortBenchmark.Initialize state) {
        Collections.sort(state.wordList);
        return state.wordList;
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
