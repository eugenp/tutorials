package com.baeldung.digitsandnondigits;

import static com.baeldung.digitsandnondigits.SplitDigitsAndNondigitsUnitTest.parseString;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Threads(1)
@BenchmarkMode(Mode.Throughput)
@Fork(warmups = 1, value = 1)
@Warmup(iterations = 2, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkLiveTest {
    private static final String INPUT = "01Michael Jackson23Michael Jordan42Michael Bolton999Michael Johnson000";

    @Param({ "10000" })
    public int iterations;

    @Benchmark
    public void regexBased(Blackhole blackhole) {
        blackhole.consume(INPUT.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"));
    }

    @Benchmark
    public void nonRegexBased(Blackhole blackhole) {
        blackhole.consume(parseString(INPUT));
    }

    @Test
    public void benchmark() throws Exception {
        String[] argv = {};
        org.openjdk.jmh.Main.main(argv);
    }
}