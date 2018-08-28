package com.baeldung.string;

import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
public class StringPerformance {

    @State(Scope.Thread)
    public static class MyState {
        int iterations = 10000;

        String sample = "example";
        String longString = "Hello, I am a bit longer than other Strings in average";
        String result = "";
    }

//    @Benchmark
//    public void benchmarkStringDynamicConcat(StringPerformance.MyState state) {
//        for (int i = 0; i < state.iterations; i++) {
//            state.result += state.sample;
//        }
//    }

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

//    @Benchmark
//    public void benchmarkStringIntern(StringPerformance.MyState state) {
//        for (int i = 0; i < state.iterations; i++) {
//            String number = Integer.toString( i );
//            String interned = number.intern();
//        }
//    }

    @Benchmark
    public void benchmarkStringReplace(StringPerformance.MyState state) {
        String replaced = state.longString.replace("average", " average !!!");
    }

    @Benchmark
    public void benchmarkStringUtilsReplace(StringPerformance.MyState state) {
        String replaced = StringUtils.replace(state.longString, "average", " average !!!");
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(StringPerformance.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
