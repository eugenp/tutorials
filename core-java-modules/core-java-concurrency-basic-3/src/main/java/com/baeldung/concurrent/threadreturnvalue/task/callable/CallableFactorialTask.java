package com.baeldung.concurrent.threadreturnvalue.task.callable;

import static com.baeldung.concurrent.threadreturnvalue.task.FactorialCalculator.factorial;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class CallableFactorialTask implements Callable<BigInteger> {

    private final Integer value;

    public CallableFactorialTask(int value) {
        this.value = value;
    }

    @Override
    public BigInteger call() {
        return factorial(BigInteger.valueOf(value));
    }
}
