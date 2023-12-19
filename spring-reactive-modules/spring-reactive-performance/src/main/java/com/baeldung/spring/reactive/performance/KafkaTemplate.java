package com.baeldung.spring.reactive.performance;

import java.util.concurrent.CompletableFuture;

public class KafkaTemplate<K, V> {

    // For simplicity in this example and article, an actual Kafka client isn't utilized.
    // The focus remains on demonstrating the basic principles without the complexities of a full Kafka client setup.

    public CompletableFuture<Void> send(String topic, K key, V value) {
        System.out.println("Sending message to topic: " + topic + " with value: " + value);
        return CompletableFuture.completedFuture(null);
    }
}