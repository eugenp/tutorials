package com.baeldung.workstealing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class PrimeNumbers extends RecursiveAction {

    private int lowerBound;
    private int upperBound;
    private int granularity;
    static final List<Integer> GRANULARITIES
      = Arrays.asList(1, 10, 100, 1000, 10000);

    PrimeNumbers(int lowerBound, int upperBound) {
        this(lowerBound, upperBound, 100);
    }

    private PrimeNumbers(int lowerBound, int upperBound, int granularity) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.granularity = granularity;
    }

    private List<PrimeNumbers> subTasks() {
        List<PrimeNumbers> subTasks = new ArrayList<>();

        for (int i = 1; i <= this.upperBound / granularity; i++) {
            int upper = i * granularity;
            int lower = (upper - granularity) + 1;
            subTasks.add(new PrimeNumbers(lower, upper));
        }
        return subTasks;
    }

    @Override
    protected void compute() {
        if (((upperBound + 1) - lowerBound) > granularity) {
            ForkJoinTask.invokeAll(subTasks());
        } else {
            findPrimeNumbers();
        }
    }

    int findPrimeNumbers() {
        int number = 0;
        for (int num = lowerBound; num <= upperBound; num++) {
            if (isPrime(num)) {
                number++;
            }
        }
        return number;
    }

    private boolean isPrime(int number) {
        if (number == 2) {
            return true;
        }

        if (number == 1 || number % 2 == 0) {
            return false;
        }

        int noOfNaturalNumbers = 0;

        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                noOfNaturalNumbers++;
            }
        }

        return noOfNaturalNumbers == 2;
    }

    static synchronized void stealInfo(StringBuilder info, int granularity, ForkJoinPool pool) {
        PrimeNumbers primes = new PrimeNumbers(1, 10000, granularity);
        invokeAndShutdown(pool, primes);

        long steals = pool.getStealCount();
        String output = "\nGranularity: [" + granularity + "], Steals: [" + steals + "]";
        info.append(output);
    }

    static void invokeAndShutdown(ForkJoinPool pool, PrimeNumbers primes) {
        pool.invoke(primes);
        pool.shutdown();
    }
}
