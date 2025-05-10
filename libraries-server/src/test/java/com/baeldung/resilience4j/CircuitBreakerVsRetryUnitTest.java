package com.baeldung.resilience4j;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

public class CircuitBreakerVsRetryUnitTest {

    interface PaymentService {

        String processPayment(int i);
    }

    private PaymentService paymentService;

    @Before
    public void setUp() {
        paymentService = mock(PaymentService.class);
    }

    @Test
    public void whenRetryWithExponentialBackoffIsUsed_thenItRetriesAndSucceeds() {
        IntervalFunction intervalFn = IntervalFunction.ofExponentialBackoff(1000, 2);
        RetryConfig retryConfig = RetryConfig.custom()
            .maxAttempts(5)
            .intervalFunction(intervalFn)
            .build();

        Retry retry = Retry.of("paymentRetry", retryConfig);

        when(paymentService.processPayment(1)).thenThrow(new RuntimeException("First Failure"))
            .thenThrow(new RuntimeException("Second Failure"))
            .thenReturn("Success");

        Callable<String> decoratedCallable = Retry.decorateCallable(retry, () -> paymentService.processPayment(1));

        try {
            String result = decoratedCallable.call();
            assertEquals("Success", result);
        } catch (Exception ignored) {

        }

        verify(paymentService, times(3)).processPayment(1);
    }

    @Test
    public void whenCircuitBreakerTransitionsThroughStates_thenBehaviorIsVerified() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .slidingWindowSize(5)
            .permittedNumberOfCallsInHalfOpenState(3)
            .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("paymentCircuitBreaker", circuitBreakerConfig);

        AtomicInteger callCount = new AtomicInteger(0);

        when(paymentService.processPayment(anyInt())).thenAnswer(invocationOnMock -> {
            callCount.incrementAndGet();
            throw new RuntimeException("Service Failure");
        });

        Callable<String> decoratedCallable = CircuitBreaker.decorateCallable(circuitBreaker, () -> paymentService.processPayment(1));

        for (int i = 0; i < 10; i++) {
            try {
                decoratedCallable.call();
            } catch (Exception ignored) {

            }
        }

        assertEquals(5, callCount.get());
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());

        callCount.set(0);
        circuitBreaker.transitionToHalfOpenState();

        assertEquals(CircuitBreaker.State.HALF_OPEN, circuitBreaker.getState());
        reset(paymentService);
        when(paymentService.processPayment(anyInt())).thenAnswer(invocationOnMock -> {
            callCount.incrementAndGet();
            return "Success";
        });

        for (int i = 0; i < 3; i++) {
            try {
                decoratedCallable.call();
            } catch (Exception ignored) {

            }
        }

        assertEquals(3, callCount.get());
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }
}
