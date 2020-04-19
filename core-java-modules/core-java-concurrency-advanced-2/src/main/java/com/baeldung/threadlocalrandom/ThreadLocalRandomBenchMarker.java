package com.baeldung.threadlocalrandom;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ThreadLocalRandomBenchMarker {
    private final Random random = new Random();

    @Benchmark
    public int randomValuesUsingRandom() {
        return random.nextInt();
    }

    @Benchmark
    public int randomValuesUsingThreadLocalRandom() {
        return ThreadLocalRandom
                .current()
                .nextInt();
    }

}
