package com.baeldung.reactive.kafka.stream.binder.domain.currency;

import reactor.core.publisher.Mono;

public interface CurrencyRate {
    Mono<Double> convertRate(String from, String to, double amount);
}
