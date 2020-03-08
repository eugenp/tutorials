package com.baeldung.workstealing;

import org.junit.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class PrimeNumbersUnitTest {

    private static Logger logger = Logger.getAnonymousLogger();

    @Test
    public void givenNewWorkStealingPool_whenGettingPrimes_thenStealCountChanges() {
        StringBuilder info = new StringBuilder();

        for (int granularity : PrimeNumbers.GRANULARITIES) {
            int parallelism = ForkJoinPool.getCommonPoolParallelism();
            ForkJoinPool pool =
              (ForkJoinPool) Executors.newWorkStealingPool(parallelism);

            stealCountInfo(info, granularity, pool);
        }
        logger.info("\nExecutors.newWorkStealingPool ->" + info.toString());
    }

    @Test
    public void givenCommonPool_whenGettingPrimes_thenStealCountChangesSlowly() {
        StringBuilder info = new StringBuilder();

        for (int granularity : PrimeNumbers.GRANULARITIES) {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            stealCountInfo(info, granularity, pool);
        }
        logger.info("\nForkJoinPool.commonPool ->" + info.toString());
    }

    private void stealCountInfo(StringBuilder info, int granularity, ForkJoinPool forkJoinPool) {
        PrimeNumbers primes = new PrimeNumbers(1, 10000, granularity, new AtomicInteger(0));
        forkJoinPool.invoke(primes);
        forkJoinPool.shutdown();

        long steals = forkJoinPool.getStealCount();
        String output = "\nGranularity: [" + granularity + "], Steals: [" + steals + "]";
        info.append(output);
    }
}
