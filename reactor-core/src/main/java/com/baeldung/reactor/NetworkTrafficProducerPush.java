package com.baeldung.reactor;

import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink.OverflowStrategy;

public class NetworkTrafficProducerPush {

    Consumer<String> listener;

    public void subscribe(Consumer<String> consumer) {
        Flux<String> flux = Flux.push(sink -> {
            NetworkTrafficProducerPush.this.listener = (t) -> sink.next(t);
        }, OverflowStrategy.DROP);
        flux.subscribe(consumer);
    }

    public void onPacket(String packet) {
        listener.accept(packet);
    }

}
