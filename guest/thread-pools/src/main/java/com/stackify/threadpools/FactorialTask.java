package com.stackify.threadpools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactorialTask extends RecursiveTask<BigInteger> {
    
    private static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private static final long serialVersionUID = 1L;

    private int start = 1;
    private int n;

    private static final int THRESHOLD = 20;

    public FactorialTask(int n) {
        this.n = n;
    }

    public FactorialTask(int start, int n) {
        logger.info("New FactorialTask Created");
        this.start = start;
        this.n = n;
    }

    @Override
    protected BigInteger compute() {        
        if ((n - start) >= THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                .stream()
                .map(ForkJoinTask::join)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        } else {
            return calculate(start, n);
        }
    }

    private Collection<FactorialTask> createSubtasks() {
        List<FactorialTask> dividedTasks = new ArrayList<>();

        int mid = (start + n) / 2;

        dividedTasks.add(new FactorialTask(start, mid));
        dividedTasks.add(new FactorialTask(mid + 1, n));
        return dividedTasks;
    }

    private BigInteger calculate(int start, int n) {
        logger.info("Calculate factorial from " + start + " to " + n);
        return IntStream.rangeClosed(start, n)
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger.ONE, BigInteger::multiply);
    }

}
