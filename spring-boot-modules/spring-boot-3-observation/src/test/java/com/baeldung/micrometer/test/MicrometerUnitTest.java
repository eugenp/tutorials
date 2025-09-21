package com.baeldung.micrometer.test;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

class MicrometerUnitTest {

    private MeterRegistry meterRegistry= new SimpleMeterRegistry();
    private FooService fooService = new FooService(meterRegistry);

    @Test
    void whenFooIsCalled_thenCounterIsIncremented() {
        fooService.foo();
        fooService.foo();
        fooService.foo();

        double invocations = meterRegistry.get("foo.count")
            .counter()
            .count();

        assertThat(invocations)
            .isEqualTo(3);
    }

    @Test
    void whenFooIsCalled_thenTimerIsUpdated() {
        fooService.foo();
        fooService.foo();
        fooService.foo();

        int totalTimeMs = (int) meterRegistry.get("foo.time")
            .timer()
            .totalTime(TimeUnit.MILLISECONDS);

        assertThat(ofMillis(totalTimeMs))
            .isBetween(ofMillis(30), ofMillis(400));
    }
}
