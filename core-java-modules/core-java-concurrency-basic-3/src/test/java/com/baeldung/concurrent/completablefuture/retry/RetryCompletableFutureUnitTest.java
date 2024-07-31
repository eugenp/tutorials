package com.baeldung.concurrent.completablefuture.retry;

import static com.baeldung.concurrent.completablefuture.retry.RetryCompletableFuture.retryExceptionallyAsync;
import static com.baeldung.concurrent.completablefuture.retry.RetryCompletableFuture.retryNesting;
import static com.baeldung.concurrent.completablefuture.retry.RetryCompletableFuture.retryTask;
import static com.baeldung.concurrent.completablefuture.retry.RetryCompletableFuture.retryUnsafe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RetryCompletableFutureUnitTest {
    private AtomicInteger retriesCounter = new AtomicInteger(0);

    @BeforeEach
    void beforeEach() {
        retriesCounter.set(0);
    }

    @Test
    void whenRetryingTask_thenReturnsCorrectlyAfterFourInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryTask(codeToRun, 10);

        assertThat(result.join()).isEqualTo(100);
        assertThat(retriesCounter).hasValue(4);
    }

    @Test
    void whenRetryingTask_thenThrowsExceptionAfterThreeInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryTask(codeToRun, 3);

        assertThatThrownBy(result::join)
          .isInstanceOf(CompletionException.class)
          .hasMessageContaining("IllegalStateException: Task failed after 3 attempts");
    }

    @Test
    void whenRetryingExceptionally_thenReturnsCorrectlyAfterFourInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryUnsafe(codeToRun, 10);

        assertThat(result.join()).isEqualTo(100);
        assertThat(retriesCounter).hasValue(4);
    }

    @Test
    void whenRetryingExceptionally_thenThrowsExceptionAfterThreeInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryUnsafe(codeToRun, 3);

        assertThatThrownBy(result::join)
          .isInstanceOf(CompletionException.class)
          .hasMessageContaining("RuntimeException: task failed for 3 time(s)");
    }

    @Test
    void whenRetryingExceptionallyAsync_thenReturnsCorrectlyAfterFourInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryExceptionallyAsync(codeToRun, 10);

        assertThat(result.join()).isEqualTo(100);
        assertThat(retriesCounter).hasValue(4);
    }

    @Test
    void whenRetryingExceptionallyAsync_thenThrowsExceptionAfterThreeInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryExceptionallyAsync(codeToRun, 3);

        assertThatThrownBy(result::join)
          .isInstanceOf(CompletionException.class)
          .hasMessageContaining("RuntimeException: task failed for 3 time(s)");
    }

    @Test
    void whenRetryingNesting_thenReturnsCorrectlyAfterFourInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryNesting(codeToRun, 10);

        assertThat(result.join()).isEqualTo(100);
        assertThat(retriesCounter).hasValue(4);
    }

    @Test
    void whenRetryingNesting_thenThrowsExceptionAfterThreeInvocations() {
        Supplier<Integer> codeToRun = () -> failFourTimesThenReturn(100);

        CompletableFuture<Integer> result = retryNesting(codeToRun, 3);

        assertThatThrownBy(result::join)
          .isInstanceOf(CompletionException.class)
          .hasMessageContaining("RuntimeException: task failed for 3 time(s)");
    }

    int failFourTimesThenReturn(int returnValue) {
        int retryNr = retriesCounter.get();
        System.out.println(String.format("invocation: %s, thread: %s", retryNr, Thread.currentThread().getName()));
        if (retryNr < 4) {
            retriesCounter.set(retryNr + 1);
            throw new RuntimeException(String.format("task failed for %s time(s)", retryNr));
        }
        return returnValue;
    }

}
