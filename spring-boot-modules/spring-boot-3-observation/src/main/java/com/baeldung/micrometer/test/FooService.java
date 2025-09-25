package com.baeldung.micrometer.test;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class FooService {

    private final MeterRegistry registry;

    public FooService(MeterRegistry registry) {
        this.registry = registry;
    }

    public int foo() {
        int delayedMs = registry.timer("foo.time")
            .record(this::doSomething);

        registry.counter("foo.count")
            .increment();

        return delayedMs;
    }

    private int doSomething() {
        int delayMs = ThreadLocalRandom.current()
            .nextInt(10, 100);
        try {
            Thread.sleep(delayMs);
            return delayMs;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
