package com.baeldung.string;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 10)
public class StringPerformance {

    @State(Scope.Thread)
    public static class MyState {
        int iterations = 1000;

        String result = "";
        String sample = "example";
    }

    @Benchmark
    public void benchmarkStringDynamicConcat(StringPerformance.MyState state) {
        for (int i = 0; i < state.iterations; i++) {
            state.result += state.sample;
        }
    }

//    @Benchmark
//    public void benchmarkStringConstructor(StringPerformance.MyState state) {
//        for (int i = 0; i < state.iterations; i++) {
//            String newString = new String(state.sample);
//        }
//    }
//
//    @Benchmark
//    public void benchmarkStringLiteral(StringPerformance.MyState state) {
//        for (int i = 0; i < state.iterations; i++) {
//            String newString = state.sample;
//        }
//    }
//
//    @Benchmark
//    public void benchmarkStringFormat(StringPerformance.MyState state) {
//        for (int i = 0; i < state.iterations; i++) {
//            String s = String.format("hello %d", state.sample);
//        }
//    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
