package com.baeldung.concurrent.threadreturnvalue.task.fork;

import static com.baeldung.concurrent.threadreturnvalue.task.FactorialCalculator.factorial;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class ForkFactorialTask extends RecursiveTask<BigInteger> {

    private final int start;
    private final int end;
    private final int threshold;

    public ForkFactorialTask(int end, int threshold) {
        this.start = 1;
        this.end = end;
        this.threshold = threshold;
    }

    public ForkFactorialTask(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected BigInteger compute() {

        BigInteger sum = BigInteger.ONE;

        if (end - start > threshold) {

            int middle = (end + start) / 2;

            return sum.multiply(new ForkFactorialTask(start, middle, threshold).fork()
              .join()
              .multiply(new ForkFactorialTask(middle + 1, end, threshold).fork()
                .join()));
        }

        return sum.multiply(factorial(BigInteger.valueOf(start), BigInteger.valueOf(end)));
    }
}
