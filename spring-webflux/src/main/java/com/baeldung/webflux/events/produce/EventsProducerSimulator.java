package com.baeldung.webflux.events.produce;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@Service
public class EventsProducerSimulator implements EventsProducer {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public ConnectableFlux<Object> produceEvents() {
        return Flux.create(fluxSink -> {
            executor.scheduleAtFixedRate(() -> {
                fluxSink.next(System.currentTimeMillis());
            }, 0, 1, TimeUnit.SECONDS);
        }).publish();
    }

}
