package com.baeldung.consumer;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

    @PostConstruct
    public Flux<String> consumeRecord() {
        return reactiveKafkaConsumerTemplate.receive()
            .map(ReceiverRecord::value)
            .doOnNext(msg -> log.info("Received: {}", msg));
    }

    public Flux<String> consumeAsABatch() {
        return reactiveKafkaConsumerTemplate.receive()
            .buffer(2)
            .flatMap(messages -> Flux.fromStream(messages.stream()
                .map(ReceiverRecord::value)));
    }

    public Flux<String> consumeWithLimit() {
        return reactiveKafkaConsumerTemplate.receive()
            .limitRate(2)
            .map(ReceiverRecord::value);
    }

    public Flux<String> consumeWithRetryWithBackOff(AtomicInteger attempts) {
        return reactiveKafkaConsumerTemplate.receive()
            .flatMap(msg -> attempts.incrementAndGet() < 3 ? Flux.error(new RuntimeException("Failure")) : Flux.just(msg))
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
            .map(ReceiverRecord::value);
    }
}
