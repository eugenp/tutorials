package com.baeldung.workstealing;

import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, warmups = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class PrimeNumbersBenchmarkLiveTest {

    @Test
    public void givenPrimesCalculated_whenUsingPoolsAndOneThread_thenOneThreadSlowest() {
        Options opt = new OptionsBuilder()
          .include(PrimeNumbersBenchmarkLiveTest.class.getSimpleName())
          .forks(1)
          .build();

        try {
            new Runner(opt).run();
        } catch (RunnerException e) {
            fail();
        }
    }

    @Benchmark
    public void singleThread() {
        PrimeNumbers primes = new PrimeNumbers(10000);
        primes.findPrimeNumbers(); // get prime numbers using a single thread
    }

    @Benchmark
    public void commonPoolBenchmark() {
        PrimeNumbers primes = new PrimeNumbers(10000);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(primes);
        pool.shutdown();
    }

    @Benchmark
    public void newWorkStealingPoolBenchmark() {
        PrimeNumbers primes = new PrimeNumbers(10000);
        int parallelism = ForkJoinPool.getCommonPoolParallelism();
        ForkJoinPool stealer = (ForkJoinPool) Executors.newWorkStealingPool(parallelism);
        stealer.invoke(primes);
        stealer.shutdown();
    }
}
