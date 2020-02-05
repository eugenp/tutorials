package com.baeldung.patternreuse;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 5)
@State(Scope.Benchmark)
public class PatternPerformanceComparison {

    private static final String PATTERN = "\\d*[02468]";
    private static List<String> values;

    private static Matcher matcherFromPreCompiledPattern;
    private static Pattern preCompiledPattern;

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void matcherFromPreCompiledPatternResetMatches(Blackhole bh) {
        //With pre-compiled pattern and reusing the matcher
        // 1 Pattern object created
        // 1 Matcher objects created
        for (String value : values) {
            bh.consume(matcherFromPreCompiledPattern.reset(value).matches());
        }
    }

    @Benchmark
    public void preCompiledPatternMatcherMatches(Blackhole bh) {
        // With pre-compiled pattern
        // 1         Pattern object created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(preCompiledPattern.matcher(value).matches());
        }
    }

    @Benchmark
    public void patternCompileMatcherMatches(Blackhole bh) {
        // Above approach "Pattern.matches(PATTERN, value)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(Pattern.compile(PATTERN).matcher(value).matches());
        }
    }

    @Benchmark
    public void patternMatches(Blackhole bh) {
        // Above approach "value.matches(PATTERN)" makes this internally
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        for (String value : values) {
            bh.consume(Pattern.matches(PATTERN, value));
        }
    }

    @Benchmark
    public void stringMatchs(Blackhole bh) {
        // 5_000_000 Pattern objects created
        // 5_000_000 Matcher objects created
        Instant start = Instant.now();
        for (String value : values) {
            bh.consume(value.matches(PATTERN));
        }
    }

    @Setup()
    public void setUp() {
        preCompiledPattern = Pattern.compile(PATTERN);
        matcherFromPreCompiledPattern = preCompiledPattern.matcher("");

        values = new ArrayList<>();
        for (int x = 1; x <= 5_000_000; x++) {
            values.add(String.valueOf(x));
        }
    }
}
