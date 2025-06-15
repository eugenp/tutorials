package com.baeldung.failsafe;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import dev.failsafe.CircuitBreaker;
import dev.failsafe.Failsafe;
import dev.failsafe.FailsafeExecutor;

public class CircuitBreakerUnitTest {
    @Test
    void test() throws InterruptedException {
        CircuitBreaker<Object> circuitBreaker = CircuitBreaker.builder()
            .withFailureThreshold(5)
            .withDelay(Duration.ofMillis(400))
            .withSuccessThreshold(4)
            .build();

        FailsafeExecutor<Object> executor = Failsafe.with(circuitBreaker);

        for (int i = 0; i < 30; ++i) {
            int finalI = i;
            System.out.println("Calling " + finalI + " - " + circuitBreaker.getState());

            try {
                executor.run(() -> {
                    System.out.println("Called " + finalI + " - " + circuitBreaker.getState());
                    if (circuitBreaker.getState() == CircuitBreaker.State.CLOSED) {
                        throw new Exception();
                    }
                });
            } catch (Exception e) {}
            Thread.sleep(100);
        }
    }
}
