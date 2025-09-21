package com.baeldung.micrometer.test;

import static java.time.Duration.ofMillis;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.micrometer.core.instrument.MeterRegistry;

@SpringBootTest(
    classes = { FooApplication.class }
)
class MicrometerIntegrationTest {

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private FooService fooService;

    @AfterEach
    void reset() {
        meterRegistry.clear();
    }

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
