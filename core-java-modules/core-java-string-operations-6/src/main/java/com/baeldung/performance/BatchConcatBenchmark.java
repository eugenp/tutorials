package com.baeldung.performance;

import java.util.List;
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
public class BatchConcatBenchmark {

    private static final String TOKEN = "string";
    private static final String[] DATA_100 = prepareData(100);
    private static final String[] DATA_1000 = prepareData(1000);
    private static final String[] DATA_10000 = prepareData(10000);

    private static final String FORMAT_STR_100 = getFormatStr(100);
    private static final String FORMAT_STR_1000 = getFormatStr(1000);
    private static final String FORMAT_STR_10000 = getFormatStr(10000);

    private static String[] prepareData(int size) {
        String[] data = new String[size];
        for (int n=0;n<size;n++) data[n] = TOKEN;
        return data;
    }

    private static String getFormatStr(int iterations) {
        StringBuilder builder = new StringBuilder();
        for (int n=0;n<iterations;n++) builder.append("%s");
        return builder.toString();
    }

    @State(Scope.Thread)
    public static class StrState {
        public String token = TOKEN;
    }

    @Benchmark
    public static void concatByPlusBy100(StrState strState, Blackhole blackhole) {
        String concatString = strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token;
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByPlusBy1000(StrState strState, Blackhole blackhole) {
        String concatString = strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token +
            strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token+ strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token + strState.token;
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByJoinBy100(Blackhole blackhole) {
        concatByJoin(DATA_100, blackhole);
    }

    @Benchmark
    public static void concatByJoinBy1000(Blackhole blackhole) {
        concatByJoin(DATA_1000, blackhole);
    }

    @Benchmark
    public static void concatByJoinBy10000(Blackhole blackhole) {
        concatByJoin(DATA_10000, blackhole);
    }

    public static void concatByJoin(String[] data, Blackhole blackhole) {
        String concatString = String.join("", data);
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByFormatBy100(Blackhole blackhole) {
        concatByFormat(FORMAT_STR_100, DATA_100, blackhole);
    }

    @Benchmark
    public static void concatByFormatBy1000(Blackhole blackhole) {
        concatByFormat(FORMAT_STR_1000, DATA_1000, blackhole);
    }

    @Benchmark
    public static void concatByFormatBy10000(Blackhole blackhole) {
        concatByFormat(FORMAT_STR_10000, DATA_10000, blackhole);
    }

    public static void concatByFormat(String formatStr, String[] data, Blackhole blackhole) {
        String concatString = String.format(formatStr, data);
        blackhole.consume(concatString);
    }

    @Benchmark
    public static void concatByStreamBy100(Blackhole blackhole) {
        concatByStream(DATA_100, blackhole);
    }

    @Benchmark
    public static void concatByStreamBy1000(Blackhole blackhole) {
        concatByStream(DATA_1000, blackhole);
    }

    @Benchmark
    public static void concatByStreamBy10000(Blackhole blackhole) {
        concatByStream(DATA_10000, blackhole);
    }

    public static void concatByStream(String[] data, Blackhole blackhole) {
        String concatString = "";
        List<String> strList = List.of(data);
        concatString = strList.stream().collect(Collectors.joining(""));
        blackhole.consume(concatString);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
            .include(BatchConcatBenchmark.class.getSimpleName()).threads(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server").build();
        new Runner(options).run();
    }

}