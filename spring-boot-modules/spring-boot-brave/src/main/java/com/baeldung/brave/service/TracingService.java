package com.baeldung.brave.service;

import brave.Span;
import brave.Tracer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class TracingService {

    private final Tracer tracer;

    public TracingService(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostConstruct
    private void postConstruct() {
        Span span = tracer.nextSpan().name("Hello from Service").start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            span.finish();
        }
    }
}
