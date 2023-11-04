package com.baeldung.asyncwithretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsyncEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEventService.class);

    private final DownstreamService downstreamService;

    @Autowired
    public AsyncEventService(DownstreamService downstreamService) {
        this.downstreamService = downstreamService;
    }

    @Async
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public Future<String> processEvents(List<String> events) {
        LOGGER.info("processing asynchronously events with Thread {}", Thread.currentThread().getName());
        downstreamService.publishEvents(events);
        CompletableFuture<String> future = new CompletableFuture<>();
        future.complete("Completed");
        return future;
    }
}
