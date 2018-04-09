package com.baeldung.reactive.components;

import java.time.Duration;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

public class EventsEmitterService {
    
    private static ConnectableFlux<Long> instance;
    
    //Will start emit events on class load infinitely
    static {
        instance = Flux.interval(Duration.ofSeconds(1)).publish();
        instance.connect();
    }

    public static ConnectableFlux<Long> getInstance() {
        return instance;
    }
}
