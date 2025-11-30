package com.baeldung.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SecondAsyncService {

    /**
     * Executes asynchronously and returns a CompletableFuture holding the result.
     * The method directly uses CompletableFuture, avoiding the deprecated AsyncResult.
     * * @return A CompletableFuture containing the result string.
     * @throws InterruptedException if the sleep is interrupted.
     */
    @Async
    public CompletableFuture<String> asyncGetData() throws InterruptedException {
        // Simulate a long-running task
        Thread.sleep(4000);
        
        // Return the result wrapped in a completed CompletableFuture
        return CompletableFuture.completedFuture(
            super.getClass().getSimpleName() + " response !!! "
        );
    }
}
