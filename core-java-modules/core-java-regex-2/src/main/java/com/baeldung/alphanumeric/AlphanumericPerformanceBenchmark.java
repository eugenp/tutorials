package com.baeldung.alphanumeric;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@Warmup(iterations = 1)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MINUTES)
@Fork(1)
public class AlphanumericPerformanceBenchmark {

    private static final String TEST_STRING = "ABC123abc123";
    private static final String REGEX = "[^[a-zA-Z0-9]*$]";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericRegex(Blackhole blackhole) {
        final Matcher matcher = PATTERN.matcher(TEST_STRING);
        boolean result = matcher.matches();
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericRegexDirectlyOnString(Blackhole blackhole) {
        boolean result = TEST_STRING.matches(REGEX);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericIteration(Blackhole blackhole) {
        boolean result = true;
        for (int i = 0; i < TEST_STRING.length(); ++i) {
            final int codePoint = TEST_STRING.codePointAt(i);
            if (!isAlphanumeric(codePoint)) {
                result = false;
                break;
            }
        }
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericIterationWithCharacterChecks(Blackhole blackhole) {
        boolean result = true;
        for (int i = 0; i < TEST_STRING.length(); ++i) {
            final int codePoint = TEST_STRING.codePointAt(i);
            if (!Character.isAlphabetic(codePoint) || !Character.isDigit(codePoint)) {
                result = false;
                break;
            }
        }
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericIterationWithCopy(Blackhole blackhole) {
        boolean result = true;
        for (final char c : TEST_STRING.toCharArray()) {
            if (!isAlphanumeric(c)) {
                result = false;
                break;
            }
        }
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void alphanumericIterationWithStream(Blackhole blackhole) {
        boolean result = TEST_STRING.chars().allMatch(this::isAlphanumeric);
        blackhole.consume(result);
    }

    public boolean isAlphanumeric(final int codePoint) {
        return (codePoint >= 65 && codePoint <= 90) ||
               (codePoint >= 97 && codePoint <= 122) ||
               (codePoint >= 48 && codePoint <= 57);
    }
}
