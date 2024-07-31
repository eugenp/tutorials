package com.baeldung.netflix.mantis.job;

import io.mantisrx.runtime.Job;
import io.mantisrx.runtime.MantisJobProvider;
import io.mantisrx.runtime.executor.LocalJobExecutorNetworked;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

public abstract class MantisJobTestBase<T> {

    private static Job jobInstance;
    Flux<T> sinkStream;

    public abstract String getSinkUrl();
    public abstract Class<T> getEventType();

    @BeforeEach
    void setUp() {
        sinkStream = getSinkStream(getSinkUrl());
    }

    @AfterAll
    static void afterAll() {
        stopJob();
    }

    protected Flux<T> getSinkStream(String sinkUrl) {
        return WebClient.builder().build().get()
          .uri(sinkUrl)
          .retrieve()
          .bodyToFlux(getEventType())
          .retryWhen(Retry.fixedDelay(10, Duration.ofMillis(2000)));
    }

    static <T> void start(MantisJobProvider<T> job) {
        jobInstance = job.getJobInstance();
        new Thread(() -> LocalJobExecutorNetworked.execute(jobInstance)).start();
    }

    static void stopJob() {
        jobInstance.getLifecycle().shutdown();
    }

}
