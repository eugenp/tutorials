package com.baeldung.reactor;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink.OverflowStrategy;

public class NetworTrafficProducerPush {

    Logger logger = LoggerFactory.getLogger(NetworTrafficProducerPush.class);

    Consumer<String> listener;

    public void subscribe(Consumer<String> consumer) {
        Flux<String> flux = Flux.push(sink -> {
            NetworTrafficProducerPush.this.listener = (t) -> sink.next(t);
        }, OverflowStrategy.DROP);
        flux.subscribe(consumer);
    }

    public void onPacket(String packet) {
        listener.accept(packet);
    }

    public static void main(String[] args) {
        NetworTrafficProducerPush trafficProducer = new NetworTrafficProducerPush();
        trafficProducer.subscribe(trafficProducer.logger::info);
        trafficProducer.onPacket("Packet[A18]");
    }

}
