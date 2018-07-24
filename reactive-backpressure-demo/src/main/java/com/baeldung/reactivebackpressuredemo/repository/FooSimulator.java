package com.baeldung.reactivebackpressuredemo.repository;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.reactivebackpressuredemo.model.Foo;

import reactor.core.publisher.Flux;

@Service
public class FooSimulator implements FooTraffic {

    private static Logger LOGGER = LoggerFactory.getLogger(FooSimulator.class);
    private static DecimalFormat plateFormatNumber = new DecimalFormat("0000");

    public FooSimulator() {
    }

    public Flux<Foo> flowTraffic() {
        LocalDateTime startTime = LocalDateTime.now();

        return Flux.<Foo> create(fluxSink -> {
            boolean inFrameTime = true;
            while (inFrameTime && !fluxSink.isCancelled()) {
                fluxSink.next(FooUtilities.simulateTraffic());
                long timeElapsed = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS);
                if (timeElapsed > 30000) {
                    LOGGER.info("TrafficSimulator finish --> With timer");
                    inFrameTime = false;
                }
            }
        })
            .share();
    }

    private static class FooUtilities {

        private static Foo simulateTraffic() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO
            }
            String id = UUID.randomUUID()
                .toString();
            return new Foo(id, "Name-" + id);
        }

    }

}
