package com.baeldung.micrometer.tags.dummy;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.micrometer.tags.Application;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.MeterTag;
import io.micrometer.core.aop.MeterTags;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Service
class DummyService {

    private static final Logger log = LoggerFactory.getLogger(DummyService.class);

    private final MeterRegistry meterRegistry;
    private final Meter.MeterProvider<Counter> counterProvider;
    private final Meter.MeterProvider<Timer> timerProvider;

    public DummyService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        this.counterProvider = Counter.builder("bar.count")
            .withRegistry(meterRegistry);
        this.timerProvider = Timer.builder("bar.time")
            .withRegistry(meterRegistry);
    }

    public String foo(String deviceType) {
        log.info("foo({})", deviceType);

        Counter.builder("foo.count")
            .tag("device.type", deviceType)
            .register(meterRegistry)
            .increment();
        String response = Timer.builder("foo.time")
            .tag("device.type", deviceType)
            .register(meterRegistry)
            .record(this::invokeSomeLogic);

        return response;
    }

    public String bar(String device) {
        log.info("bar({})", device);

        counterProvider.withTag("device.type", device)
            .increment();
        String response = timerProvider.withTag("device.type", device)
            .record(this::invokeSomeLogic);

        return response;
    }

    @Timed("buzz.time")
    @Counted("buzz.count")
    public String buzz(@MeterTag("device.type") String device) {
        log.info("buzz({})", device);
        return invokeSomeLogic();
    }

    @Counted(value = "fizz")
    public String fizz(
        @MeterTag(key = "id", expression = "#order.id")
        @MeterTag(key = "other.id", expression = "#order.otherOrder.id")
        @MeterTag(key = "math", expression = "20 - 1")
        @MeterTag(key = "total", expression = "#order.total")
        @MeterTag(key = "other.total", expression = "#order.otherOrder.total")
        @MeterTag(key = "total.group", expression = "#order.total > 50 ? 'high' : 'low'")
        Order order
    ) {
        log.info("fizz({})", order);
        return invokeSomeLogic();
    }

    public record Order(int id, int total, Order otherOrder) {
    }

    private String invokeSomeLogic() {
        long sleepMs = ThreadLocalRandom.current()
            .nextInt(0, 100);
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "dummy response";
    }

}
