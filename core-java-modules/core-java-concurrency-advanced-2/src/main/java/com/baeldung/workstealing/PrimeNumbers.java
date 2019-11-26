package com.baeldung.workstealing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class PrimeNumbers extends RecursiveAction {

    private int lower;
    private int upper;
    private int granularity = 100;

    public PrimeNumbers(int lower, int upper) {
        this(lower, upper, 100);
    }

    public PrimeNumbers(int lower, int upper, int granularity) {
        this.lower = lower;
        this.upper = upper;
        this.granularity = granularity;
    }

    private List<PrimeNumbers> subTasks() {
        List<PrimeNumbers> subTasks = new ArrayList<>();

        for (int i = 1; i <= this.upper / granularity; i++) {
            int upper = i * granularity;
            int lower = (upper - granularity) + 1;
            subTasks.add(new PrimeNumbers(lower, upper));
        }

        return subTasks;
    }

    @Override
    protected void compute() {
        if (((upper + 1) - lower) > granularity) {
            ForkJoinTask.invokeAll(subTasks());
        } else {
            findPrimeNumbers();
        }
    }

    public int findPrimeNumbers() {
        int number = 0;
        for (int num = lower; num <= upper; num++) {
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
}
