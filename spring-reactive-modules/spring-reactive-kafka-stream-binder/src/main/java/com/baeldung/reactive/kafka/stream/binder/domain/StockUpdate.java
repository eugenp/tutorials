package com.baeldung.reactive.kafka.stream.binder.domain;

import java.time.Instant;

public record StockUpdate(String symbol, double price, String currency, Instant timestamp) {

}
