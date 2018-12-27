package com.baeldung.threadlocalrandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ThreadLocalRandomBenchMarker {

    List<Callable<Integer>> randomCallables = new ArrayList<>();
    List<Callable<Integer>> threadLocalRandomCallables = new ArrayList<>();

    @Setup(Level.Iteration)
    public void init() {
        Random random = new Random();
        randomCallables = new ArrayList<>();
        threadLocalRandomCallables = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            randomCallables.add(() -> {
                return random.nextInt();
            });
        }

        for (int i = 0; i < 1000; i++) {
            threadLocalRandomCallables.add(() -> {
                return ThreadLocalRandom.current()
                    .nextInt();
            });
        }
    }

    @Benchmark
    public void randomValuesUsingRandom() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        executor.invokeAll(randomCallables);
        executor.shutdown();
    }

    @Benchmark
    public void randomValuesUsingThreadLocalRandom() throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();
        executor.invokeAll(threadLocalRandomCallables);
        executor.shutdown();
    }

}
