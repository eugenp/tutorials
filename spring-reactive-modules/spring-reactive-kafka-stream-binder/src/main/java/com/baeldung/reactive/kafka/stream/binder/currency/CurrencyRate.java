package com.baeldung.reactive.kafka.stream.binder.currency;

import reactor.core.publisher.Mono;

public interface CurrencyRate {
    Mono<Double> convertRate(String from, String to, double amount);
}
