package com.baeldung.concurrent.threadreturnvalue.callable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.baeldung.concurrent.threadreturnvalue.task.callable.CallableExecutor;
import com.baeldung.concurrent.threadreturnvalue.task.callable.CallableFactorialTask;

public class CallableUnitTest {

    private final CallableExecutor callableExecutor = new CallableExecutor();

    @Test
    void givenCallableExecutor_whenExecuteFactorial_thenResultOk() {
        BigInteger result = callableExecutor.execute(Arrays.asList(new CallableFactorialTask(5), new CallableFactorialTask(3)));
        assertEquals(BigInteger.valueOf(126), result);
    }
}