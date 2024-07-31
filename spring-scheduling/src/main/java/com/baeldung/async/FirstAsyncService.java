package com.baeldung.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class FirstAsyncService {

    @Async
    public CompletableFuture<String> asyncGetData() throws InterruptedException {
        Thread.sleep(4000);
        return new AsyncResult<>(super.getClass().getSimpleName() + " response !!! ").completable();
    }

}
