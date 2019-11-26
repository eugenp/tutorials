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
        long end = System.currentTimeMillis();
        long singleThreadedTime = end - start;

        List<ForkJoinPool> pools = Arrays.asList(
                ForkJoinPool.commonPool(),
                (ForkJoinPool) Executors.newWorkStealingPool(ForkJoinPool.getCommonPoolParallelism())
        );

        for (ForkJoinPool pool : pools) {
            start = System.currentTimeMillis();
            pool.invoke(primes); // get primes using fork/join pool
            end = System.currentTimeMillis();
            pool.shutdown();
            long poolTime = end - start;
            assertTrue(poolTime < singleThreadedTime);
        }
    }


    @Test
    public void givenNewWorkStealingPool_whenGettingPrimes_thenStealCountChanges() {
        int[] granularities = {1, 10, 100, 1000, 10000};
        StringBuilder stealInfo = new StringBuilder();

        for (int granularity : granularities) {
            PrimeNumbers primes = new PrimeNumbers(1, 10000, granularity);
            ForkJoinPool pool =
                    (ForkJoinPool) Executors.newWorkStealingPool(ForkJoinPool.getCommonPoolParallelism());
            pool.invoke(primes);
            long noOfSteals = pool.getStealCount();
            String output = String.format("\nGranularity: [%d], Steals: [%d]", granularity, noOfSteals);
            stealInfo.append(output);
            pool.shutdown();
        }
        logger.info("\nExecutors.newWorkStealingPool ->" + stealInfo.toString());
    }

    @Test
    public void givenCommonPool_whenGettingPrimes_thenStealCountChangesSlowly() {
        int[] granularities = {1, 10, 100, 1000, 10000};
        StringBuilder stealInfo = new StringBuilder();

        for (int granularity : granularities) {
            PrimeNumbers primes = new PrimeNumbers(1, 10000, granularity);
            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.invoke(primes);

            long noOfSteals = pool.getStealCount();
            String output = String.format("\nGranularity: [%d], Steals: [%d]", granularity, noOfSteals);
            stealInfo.append(output);
            pool.shutdown();
        }
        logger.info("\nForkJoinPool.commonPool ->" + stealInfo.toString());
    }

}
