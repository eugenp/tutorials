package com.baeldung.publisher;

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublisherService {

    private final ReactiveKafkaProducerTemplate<String, String> kafkaProducerTemplate;

    public Mono<Void> publish(String message, String topic) {
        return kafkaProducerTemplate.send(topic, message)
            .doOnError(error -> log.info("unable to send message due to: {}", error.getMessage()))
            .then();
    }
}