package com.baeldung.reactive.kafka.stream.binder.domain;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Instant;

public record StockUpdate(String symbol, double price, Instant timestamp) {

    // Required for Kafka serialization
    public static class StockUpdateJsonSerde extends JsonSerde<StockUpdate> { }

}
