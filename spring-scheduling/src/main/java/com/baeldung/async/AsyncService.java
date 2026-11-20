package com.baeldung.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    private FirstAsyncService firstService;
    @Autowired
    private SecondAsyncService secondService;

    public CompletableFuture<String> asyncMergeServicesResponse() throws InterruptedException {
        CompletableFuture<String> firstServiceResponse = firstService.asyncGetData();
        CompletableFuture<String> secondServiceResponse = secondService.asyncGetData();

        // Merge responses from FirstAsyncService and SecondAsyncService
        return firstServiceResponse.thenCompose(firstServiceValue -> secondServiceResponse.thenApply(secondServiceValue -> firstServiceValue + secondServiceValue));
    }
}
