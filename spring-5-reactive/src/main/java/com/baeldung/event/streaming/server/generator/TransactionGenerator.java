package com.baeldung.event.streaming.server.generator;

import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.baeldung.event.streaming.model.Transaction;

import reactor.core.publisher.Flux;

@Component
public class TransactionGenerator {

    public Flux<Transaction> generate() {
        return Flux.interval(Duration.ofSeconds(1))
            .flatMap(val -> Flux.create(fluxSink -> fluxSink.next(generateTransaction(val))));
    }

    private Transaction generateTransaction(long sequence) {
        System.out.println("Generating txn: " + sequence);
        return new Transaction(UUID.randomUUID()
            .toString(), Math.random());
    }
}
