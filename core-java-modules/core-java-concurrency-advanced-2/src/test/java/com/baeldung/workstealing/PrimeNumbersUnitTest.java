package com.baeldung.workstealing;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class PrimeNumbersUnitTest {

    private static Logger logger = Logger.getAnonymousLogger();

    @Test
    public void givenPrimesCalculated_whenUsingPoolsAndOneThread_thenOneThreadSlowest() {

        PrimeNumbers primes = new PrimeNumbers(1, 10000);
        long start = System.currentTimeMillis();
        primes.findPrimeNumbers(); // get prime numbers using a single thread
        long singleThreadTime = System.currentTimeMillis() - start;

        int parallelism = ForkJoinPool.getCommonPoolParallelism();
        List<ForkJoinPool> pools = Arrays.asList(
                ForkJoinPool.commonPool(),
                (ForkJoinPool) Executors.newWorkStealingPool(parallelism)
        );

        for (ForkJoinPool pool : pools) {
            start = System.currentTimeMillis();
            // get primes using work stealing thread pools
            PrimeNumbers.invokeAndShutdown(pool, primes);
            long poolTime = System.currentTimeMillis() - start;
            assertTrue(poolTime < singleThreadTime);
        }
    }

    @Test
    public void givenNewWorkStealingPool_whenGettingPrimes_thenStealCountChanges() {
        StringBuilder info = new StringBuilder();

        for (int granularity : PrimeNumbers.GRANULARITIES) {
            int parallelism = ForkJoinPool.getCommonPoolParallelism();
            ForkJoinPool pool =
                    (ForkJoinPool) Executors.newWorkStealingPool(parallelism);

            PrimeNumbers.stealInfo(info, granularity, pool);
        }
        logger.info("\nExecutors.newWorkStealingPool ->" + info.toString());
    }

    @Test
    public void givenCommonPool_whenGettingPrimes_thenStealCountChangesSlowly() {
        StringBuilder info = new StringBuilder();

        for (int granularity : PrimeNumbers.GRANULARITIES) {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            PrimeNumbers.stealInfo(info, granularity, pool);
        }
        logger.info("\nForkJoinPool.commonPool ->" + info.toString());
    }

}
