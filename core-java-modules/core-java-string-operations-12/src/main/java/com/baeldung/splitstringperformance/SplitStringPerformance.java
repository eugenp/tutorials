package com.baeldung.splitstringperformance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 1)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@State(Scope.Thread)
public class SplitBenchmark {

    @Param({"10", "1000", "100000"})
    public int tokenCount;

    private static final String DELIM = ",";
    private String text;
    private Pattern commaPattern;

    @Setup(Level.Trial)
    public void setup() {
        StringBuilder sb = new StringBuilder(tokenCount * 8);
        for (int i = 0; i < tokenCount; i++) {
            sb.append("token").append(i);
            if (i < tokenCount - 1) sb.append(DELIM);
        }
        text = sb.toString();
        commaPattern = Pattern.compile(",");
    }

    @Benchmark
    public void stringSplit(Blackhole bh) {
        String[] parts = text.split(DELIM);
        bh.consume(parts.length);
    }

    @Benchmark
    public void patternSplit(Blackhole bh) {
        String[] parts = commaPattern.split(text);
        bh.consume(parts.length);
    }

    @Benchmark
    public void manualSplit(Blackhole bh) {
        List<String> tokens = new ArrayList<>(tokenCount);
        int start = 0, idx;
        while ((idx = text.indexOf(DELIM, start)) >= 0) {
            tokens.add(text.substring(start, idx));
            start = idx + 1;
        }
        tokens.add(text.substring(start));
        bh.consume(tokens.size());
    }
}
