package com.baeldung.micrometer.tags.rest;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.MeterTag;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@RestController
class DummyRestController {

    private static final Logger LOG = LoggerFactory.getLogger(DummyRestController.class);

    private final MeterRegistry meterRegistry;

    public DummyRestController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/api/dummy")
    @Timed(value = "dummy.endpoint.time", description = "Time taken to return dummy response") // works ok
    @Counted(value = "dummy.endpoint.count", description = "Counting dummy requests") // doesn't pick up the tag
    public String dummy(
        @RequestHeader("User-Agent")
        @MeterTag(value = "user_agent")
        String userAgent
    ) {
        LOG.info("[{}] GET /api/dummy", userAgent);
        return doStuff();
    }

    @GetMapping("/api/foo")
    public String foo(
        @RequestHeader("User-Agent")
        @MeterTag(value = "user_agent_type")
        String userAgent
    ) {
        LOG.info("[{}] GET /api/foo", userAgent);

        Counter.builder("foo.endpoint.count")
            .tag("user_agent", userAgent)
            .register(meterRegistry)
            .increment();

        String response = Timer.builder("foo.endpoint.time")
            .tag("user_agent", userAgent)
            .register(meterRegistry)
            .record(this::doStuff);

        return response;
    }


    private String doStuff() {
        long sleepMs = ThreadLocalRandom.current().nextInt(0, 100);
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "response";
    }

}
