package com.baeldung.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 1)
public class LoopConcatBenchmark {

    private static final String TOKEN = "string";
    private static final int ITERATION_100 = 100;
    private static final int ITERATION_1000 = 1000;
    private static final int ITERATION_10000 = 10000;

    @Benchmark
    public static void concatByPlusBy100(Blackhole blackhole) {
        concatByPlus(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByPlusBy1000(Blackhole blackhole) {
        concatByPlus(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByPlusBy10000(Blackhole blackhole) {
        concatByPlus(ITERATION_10000, blackhole);
    }

    public static void concatByPlus(int iterations, Blackhole blackhole) {
        String concatString = "";
        for (int n=0;n<iterations;n++) concatString += TOKEN;
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByConcatBy100(Blackhole blackhole) {
        concatByConcat(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByConcatBy1000(Blackhole blackhole) {
        concatByConcat(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByConcatBy10000(Blackhole blackhole) {
        concatByConcat(ITERATION_10000, blackhole);
    }

    public static void concatByConcat(int iterations, Blackhole blackhole) {
        String concatString = "";
        for (int n=0;n<iterations;n++) concatString = concatString.concat(TOKEN);
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByJoinBy100(Blackhole blackhole) {
        concatByJoin(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByJoinBy1000(Blackhole blackhole) {
        concatByJoin(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByJoinBy10000(Blackhole blackhole) {
        concatByJoin(ITERATION_10000, blackhole);
    }

    public static void concatByJoin(int iterations, Blackhole blackhole) {
        String concatString = "";
        for (int n=0;n<iterations;n++) concatString = String.join("", concatString, TOKEN);
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByFormatBy100(Blackhole blackhole) {
        concatByFormat(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByFormatBy1000(Blackhole blackhole) {
        concatByFormat(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByFormatBy10000(Blackhole blackhole) {
        concatByFormat(ITERATION_10000, blackhole);
    }

    public static void concatByFormat(int iterations, Blackhole blackhole) {
        String concatString = "";
        for (int n=0;n<iterations;n++) concatString = String.format("%s%s", concatString, TOKEN);
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByStringJoinerBy100(Blackhole blackhole) {
        concatByStringJoiner(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByStringJoinerBy1000(Blackhole blackhole) {
        concatByStringJoiner(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByStringJoinerBy10000(Blackhole blackhole) {
        concatByStringJoiner(ITERATION_10000, blackhole);
    }

    public static void concatByStringJoiner(int iterations, Blackhole blackhole) {
        StringJoiner concatBuf = new StringJoiner("");
        for (int n=0; n<iterations; n++) concatBuf.add(TOKEN);
        blackhole.consume(concatBuf);
    }

    @Benchmark
    public static void concatByStringBufferBy100(Blackhole blackhole) {
        concatByStringBuffer(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByStringBufferBy1000(Blackhole blackhole) {
        concatByStringBuffer(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByStringBufferBy10000(Blackhole blackhole) {
        concatByStringBuffer(ITERATION_10000, blackhole);
    }

    public static void concatByStringBuffer(int iterations, Blackhole blackhole) {
        StringBuffer concatBuf = new StringBuffer();
        for (int n=0; n<iterations; n++) concatBuf.append(TOKEN);
        blackhole.consume(concatBuf);
    }

    @Benchmark
    public static void concatByStringBuilderBy100(Blackhole blackhole) {
        concatByStringBuilder(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByStringBuilderBy1000(Blackhole blackhole) {
        concatByStringBuilder(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByStringBuilderBy10000(Blackhole blackhole) {
        concatByStringBuilder(ITERATION_10000, blackhole);
    }

    public static void concatByStringBuilder(int iterations, Blackhole blackhole) {
        StringBuilder concatBuf = new StringBuilder();
        for (int n=0; n<iterations; n++) concatBuf.append(TOKEN);
        blackhole.consume(concatBuf);
    }

    @Benchmark
    public static void concatByStreamBy100(Blackhole blackhole) {
        concatByStream(ITERATION_100, blackhole);
    }

    @Benchmark
    public static void concatByStreamBy1000(Blackhole blackhole) {
        concatByStream(ITERATION_1000, blackhole);
    }

    @Benchmark
    public static void concatByStreamBy10000(Blackhole blackhole) {
        concatByStream(ITERATION_10000, blackhole);
    }

    public static void concatByStream(int iterations, Blackhole blackhole) {
        String concatString = "";
        List<String> strList = new ArrayList<>();
        strList.add(concatString);
        strList.add(TOKEN);
        for (int n=0; n<iterations; n++) {
            concatString = strList.stream().collect(Collectors.joining(""));
            strList.set(0, concatString);
        }
        blackhole.consume(concatString);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
            .include(LoopConcatBenchmark.class.getSimpleName()).threads(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server").build();
        new Runner(options).run();
    }

}