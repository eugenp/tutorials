package com.baeldung.webflux.events.produce;

import reactor.core.publisher.ConnectableFlux;

public interface EventsProducer {

    public ConnectableFlux<Object> produceEvents();
}
