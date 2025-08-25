package com.java.baeldung.spring.test;

import jakarta.annotation.PreDestroy;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SampleService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);

    private final SampleBean sampleBean;

    public SampleService(SampleBean sampleBean) {
        this.sampleBean = sampleBean;
    }

    public void scheduleNow(Runnable command, long periodSeconds) {
        scheduler.scheduleAtFixedRate(command, 0L, periodSeconds, TimeUnit.SECONDS);
    }

    public String getValue() {
        return sampleBean.getValue();
    }

    // to avoid thread leakage in test execution
    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
    }
}
