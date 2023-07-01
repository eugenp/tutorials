package com.baeldung.concurrent.completablefuture.threadpool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class CompletableFutureThreadPoolUnitTest {

    @Test
    void whenUsingNonAsync_thenUsesMainThread() {
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

        CompletableFuture<Integer> nameLength = name.thenApply(value -> {
            printCurrentThread();
            return value.length();
        });

        assertThat(nameLength).isCompletedWithValue(8);
    }

    @Test
    void whenUsingNonAsync_thenUsesCallersThread() throws InterruptedException {
        Runnable test = () -> {
            CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

            CompletableFuture<Integer> nameLength = name.thenApply(value -> {
                printCurrentThread();
                return value.length();
            });

            assertThat(nameLength).isCompletedWithValue(8);
        };

        new Thread(test, "test-thread").start();
        Thread.sleep(100l);
    }

    @Test
    void whenUsingAsync_thenUsesCommonPool() {
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

        CompletableFuture<Integer> nameLength = name.thenApplyAsync(value -> {
            printCurrentThread();
            return value.length();
        });

        assertThat(nameLength).isCompletedWithValue(8);
    }

    @Test
    void whenUsingAsync_thenUsesCustomExecutor() {
        Executor testExecutor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> "Baeldung");

        CompletableFuture<Integer> nameLength = name.thenApplyAsync(value -> {
            printCurrentThread();
            return value.length();
        }, testExecutor);

        assertThat(nameLength).isCompletedWithValue(8);
    }

    @Test
    void whenOverridingDefaultThreadPool_thenUsesCustomExecutor() {
        CompletableFuture<String> name = CustomCompletableFuture.supplyAsync(() -> "Baeldung");

        CompletableFuture<Integer> nameLength = name.thenApplyAsync(value -> {
            printCurrentThread();
            return value.length();
        });

        assertThat(nameLength).isCompletedWithValue(8);
    }

    private static void printCurrentThread() {
        System.out.println(Thread.currentThread().getName());
    }
}
