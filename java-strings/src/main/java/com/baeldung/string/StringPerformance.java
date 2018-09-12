package com.baeldung.string;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10)
public class StringPerformance extends StringPerformanceTests {

    @State(Scope.Thread)
    public static class MyState {
        int iterations = 100000;

        String sample = "baeldung";
        String longString = "Hello baeldung, I am a bit longer than other Strings";
        String result = "";
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringDynamicConcat(StringPerformance.MyState state) {
        return dynamicConcat(invocations_100_000);
    }

    @Benchmark
    public StringBuilder  benchmarkStringBuilder(StringPerformance.MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.result);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.sample);
        }

        return stringBuilder;
    }

    @Benchmark
    public StringBuffer benchmarkStringBuffer(StringPerformance.MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.result);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.sample);
        }

        return stringBuffer;
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringConstructor(StringPerformance.MyState state) {
        return StringConstructor(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringLiteral() {
        return StringLiteral(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringFormat_s() {
        return stringFormat_s(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringFormat_d() {
        return stringFormat_d(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringConcat(StringPerformance.MyState state) {
        return stringConcat(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringIntern(StringPerformance.MyState state) {
        return stringIntern(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringReplace(StringPerformance.MyState state) {
        return state.longString.replace("average", " average !!!");
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringUtilsReplace(StringPerformance.MyState state) {
        return StringUtils.replace(state.longString, "average", " average !!!");
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public List<String> benchmarkGuavaSplitter(StringPerformance.MyState state) {
        return guavaSplitter(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String [] benchmarkStringSplit(StringPerformance.MyState state) {
        return stringSplit(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String [] benchmarkStringSplitPattern(StringPerformance.MyState state) {
        return stringSplitPattern(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public List benchmarkStringTokenizer() {
        return stringTokenizer(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public List benchmarkStringIndexOf() {
        return stringIndexOf(invocations_100_000);
    }


    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkIntegerToString() {
        return stringIntegerToString(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringValueOf() {
        return stringValueOf(invocations_100_000);
    }


    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public String benchmarkStringConvertPlus() {
        return stringConvertPlus(invocations_100_000);
    }


    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkStringEquals() {
        return stringEquals(invocations_100_000);
    }


    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkStringEqualsIgnoreCase() {
        return stringEqualsIgnoreCase(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkStringMatches() {
        return stringIsMatch(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkPrecompiledMatches() {
        return precompiledMatches(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public int benchmarkStringCompareTo() {
        return stringCompareTo(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkStringIsEmpty() {
        return stringIsEmpty(invocations_100_000);
    }

    @Benchmark
    @OperationsPerInvocation(invocations_100_000)
    public boolean benchmarkStringLengthZero() {
        return stringLengthZero(invocations_100_000);
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
