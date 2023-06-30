package com.baeldung.concurrent.threadreturnvalue.task.fork;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ForkExecutor {

    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    public BigInteger execute(ForkFactorialTask forkFactorial) {
        return forkJoinPool.invoke(forkFactorial);
    }

    public BigInteger execute(List<Callable<BigInteger>> forkFactorials) {
        List<Future<BigInteger>> futures = forkJoinPool.invokeAll(forkFactorials);

        BigInteger result = BigInteger.ZERO;

        for (Future<BigInteger> future : futures) {
            try {
                result = result.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                // exception handling example
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
