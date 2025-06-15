package com.baeldung.failsafe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;
import dev.failsafe.Fallback;

public class FallbackUnitTest {
    @Test
    void fallbackOnSuccessTest() {
        Fallback<Integer> policy = Fallback.builder(0)
            .build();

        Integer result = Failsafe.with(policy).get(() -> 1);

        assertEquals(1, result);
    }

    @Test
    void fallbackOnExceptionTest() {
        Fallback<Integer> policy = Fallback.builder(0)
            .build();

        Integer result = Failsafe.with(policy).get(() -> {throw new Exception();});

        assertEquals(0, result);
    }

    @Test
    void fallbackOnConfiguredExceptionTest() {
        Fallback<Integer> defaultPolicy = Fallback.builder(0).build();
        Fallback<Integer> npePolicy = Fallback.builder(1)
            .handle(NullPointerException.class)
            .build();
        Fallback<Integer> ioePolicy = Fallback.builder(2)
            .handleIf(e -> e instanceof IOException)
            .build();

        FailsafeExecutor<Integer> executor = Failsafe.with(defaultPolicy, npePolicy, ioePolicy);

        assertEquals(0, executor.get(() -> {throw new Exception();}));
        assertEquals(1, executor.get(() -> {throw new NullPointerException();}));
        assertEquals(2, executor.get(() -> {throw new IOException();}));
    }

    @Test
    void fallbackOnLambdaResultTest() {
        Fallback<Integer> policy = Fallback.builder(1)
            .handleResult(null)
            .handleResultIf(result -> result < 0)
            .build();

        FailsafeExecutor<Integer> executor = Failsafe.with(policy);

        assertEquals(1, executor.get(() -> null));
        assertEquals(1, executor.get(() -> -1));
        assertEquals(2, executor.get(() -> 2));
    }
}
