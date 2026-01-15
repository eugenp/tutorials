package com.baeldung.micrometer.references;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
@RequestMapping("/micrometer/references")
class FooService {

    private final MeterRegistry registry;

    public FooService(MeterRegistry registry) {
        this.registry = registry;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupGauges() {
        setupWeakReferenceGauge();
        setupStrongReferenceGauge();
    }

    private void setupWeakReferenceGauge() {
        Foo foo = new Foo(10);

        Gauge.builder("foo.weak", foo, Foo::value)
            .description("Foo value - weak reference (will show NaN after GC)")
            .register(registry);
    }

    private void setupStrongReferenceGauge() {
        Foo foo = new Foo(10);

        Gauge.builder("foo.strong", foo, Foo::value)
            .description("Foo value - strong reference (will persist)")
            .strongReference(true)
            .register(registry);
    }

    @GetMapping("/gc")
    public void triggerGC() {
        System.gc();
    }

    record Foo(int value) {

    }

}