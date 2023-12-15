package com.baeldung.concurrent.completablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.junit.Test;

public class CompletableFutureUnitTest {

    @Test
    public void givenAsyncTask_whenProcessingAsyncSucceed_thenReturnSuccess() throws ExecutionException, InterruptedException {
        Microservice mockMicroserviceA = mock(Microservice.class);
        Microservice mockMicroserviceB = mock(Microservice.class);

        when(mockMicroserviceA.retrieveAsync(any())).thenReturn(CompletableFuture.completedFuture("Hello"));
        when(mockMicroserviceB.retrieveAsync(any())).thenReturn(CompletableFuture.completedFuture("World"));

        CompletableFuture<String> resultFuture = processAsync(List.of(mockMicroserviceA, mockMicroserviceB));

        String result = resultFuture.get();
        assertEquals("HelloWorld", result);
    }

    @Test
    public void givenAsyncTask_whenProcessingAsyncWithException_thenReturnException() throws ExecutionException, InterruptedException {
        Microservice mockMicroserviceA = mock(Microservice.class);
        Microservice mockMicroserviceB = mock(Microservice.class);

        when(mockMicroserviceA.retrieveAsync(any())).thenReturn(CompletableFuture.completedFuture("Hello"));
        when(mockMicroserviceB.retrieveAsync(any())).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Simulated Exception")));
        CompletableFuture<String> resultFuture = processAsync(List.of(mockMicroserviceA, mockMicroserviceB));
        // Use assertThrows to verify that the expected exception is thrown
        ExecutionException exception = assertThrows(ExecutionException.class, resultFuture::get);
        // Assert the exception message
        assertEquals("Simulated Exception", exception.getCause()
            .getMessage());
    }

    @Test
    public void givenAsyncTask_whenProcessingAsyncWithTimeout_thenHandleTimeoutException() throws ExecutionException, InterruptedException {
        Microservice mockMicroserviceA = mock(Microservice.class);
        Microservice mockMicroserviceB = mock(Microservice.class);
        Executor delayedExecutor = CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS);
        when(mockMicroserviceA.retrieveAsync(any())).thenReturn(CompletableFuture.supplyAsync(() -> "Hello", delayedExecutor));
        Executor delayedExecutor2 = CompletableFuture.delayedExecutor(500, TimeUnit.MILLISECONDS);
        when(mockMicroserviceB.retrieveAsync(any())).thenReturn(CompletableFuture.supplyAsync(() -> "World", delayedExecutor2));
        CompletableFuture<String> resultFuture = processAsync(List.of(mockMicroserviceA, mockMicroserviceB));
        assertThrows(TimeoutException.class, () -> resultFuture.get(300, TimeUnit.MILLISECONDS));
    }

    @Test
    public void givenCompletableFuture_whenCompleted_thenStateIsDone() {
        Executor delayedExecutor = CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello", delayedExecutor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> " World");
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "!");
        CompletableFuture<String>[] cfs = new CompletableFuture[] { cf1, cf2, cf3 };

        CompletableFuture<Void> allCf = CompletableFuture.allOf(cfs);

        assertFalse(allCf.isDone());
        allCf.join();
        String result = Arrays.stream(cfs)
            .map(CompletableFuture::join)
            .collect(Collectors.joining());

        assertFalse(allCf.isCancelled());
        assertTrue(allCf.isDone());
        assertFalse(allCf.isCompletedExceptionally());
        assertEquals(result, "Hello World!");
    }

    @Test
    public void givenCompletableFuture_whenCompletedWithException_thenStateIsCompletedExceptionally() throws ExecutionException, InterruptedException {
        Executor delayedExecutor = CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello", delayedExecutor);
        CompletableFuture<String> cf2 = CompletableFuture.failedFuture(new RuntimeException("Simulated Exception"));
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "!");
        CompletableFuture<String>[] cfs = new CompletableFuture[] { cf1, cf2, cf3 };

        CompletableFuture<Void> allCf = CompletableFuture.allOf(cfs);

        assertFalse(allCf.isDone());
        assertFalse(allCf.isCompletedExceptionally());

        // Exception is expected, assert the cause
        CompletionException exception = assertThrows(CompletionException.class, allCf::join);

        assertEquals("Simulated Exception", exception.getCause()
            .getMessage());
        assertTrue(allCf.isCompletedExceptionally());
        assertTrue(allCf.isDone());
        assertFalse(allCf.isCancelled());
    }

    @Test
    public void givenCompletableFuture_whenCancelled_thenStateIsCancelled() throws ExecutionException, InterruptedException {
        Executor delayedExecutor = CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "Hello", delayedExecutor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> " World");
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> "!");
        CompletableFuture<String>[] cfs = new CompletableFuture[] { cf1, cf2, cf3 };
        CompletableFuture<Void> allCf = CompletableFuture.allOf(cfs);
        assertFalse(allCf.isDone());
        assertFalse(allCf.isCompletedExceptionally());
        allCf.cancel(true);
        assertTrue(allCf.isCancelled());
        assertTrue(allCf.isDone());
    }

    CompletableFuture<String> processAsync(List<Microservice> microservices) {
        List<CompletableFuture<String>> dataFetchFutures = fetchDataAsync(microservices);
        return combineResults(dataFetchFutures);
    }

    private List<CompletableFuture<String>> fetchDataAsync(List<Microservice> microservices) {
        return microservices.stream()
            .map(client -> client.retrieveAsync(""))
            .collect(Collectors.toList());
    }

    private CompletableFuture<String> combineResults(List<CompletableFuture<String>> dataFetchFutures) {
        return CompletableFuture.allOf(dataFetchFutures.toArray(new CompletableFuture[0]))
            .thenApply(v -> dataFetchFutures.stream()
                .map(future -> future.exceptionally(ex -> {
                        throw new CompletionException(ex);
                    })
                    .join())
                .collect(Collectors.joining()));
    }

    interface Microservice {
        CompletableFuture<String> retrieveAsync(String input);
    }
}
