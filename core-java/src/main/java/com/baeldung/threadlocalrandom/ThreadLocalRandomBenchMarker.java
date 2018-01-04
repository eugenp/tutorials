package com.baeldung.threadlocalrandom;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ThreadLocalRandomBenchMarker {

    @Benchmark
    public void randomValuesUsingRandom() {
        final ExecutorService executor = (ForkJoinPool) Executors.newWorkStealingPool();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                int randomValue = random.nextInt();
            });
        }
        executor.shutdown();
    }

    @Benchmark
    public void randomValuesUsingThreadLocalRandom() {
        final ExecutorService executor = (ForkJoinPool) Executors.newWorkStealingPool();
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                int randomValue = ThreadLocalRandom.current().nextInt();
            });
        }
        executor.shutdown();
    }

}
