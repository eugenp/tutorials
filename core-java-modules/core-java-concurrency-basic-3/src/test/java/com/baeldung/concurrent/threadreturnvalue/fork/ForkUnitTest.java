package com.baeldung.concurrent.threadreturnvalue.fork;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.baeldung.concurrent.threadreturnvalue.task.callable.CallableFactorialTask;
import com.baeldung.concurrent.threadreturnvalue.task.fork.ForkExecutor;
import com.baeldung.concurrent.threadreturnvalue.task.fork.ForkFactorialTask;

public class ForkUnitTest {

    private final ForkExecutor forkExecutor = new ForkExecutor();

    @Test
    void givenForkExecutor_whenExecuteRecursiveTask_thenResultOk() {
        assertEquals(BigInteger.valueOf(3628800), forkExecutor.execute(new ForkFactorialTask(10, 5)));
    }

    @Test
    void givenForkExecutor_whenExecuteCallable_thenResultOk() {
        assertEquals(BigInteger.valueOf(126), forkExecutor.execute(Arrays.asList(new CallableFactorialTask(5), new CallableFactorialTask(3))));
    }
}
