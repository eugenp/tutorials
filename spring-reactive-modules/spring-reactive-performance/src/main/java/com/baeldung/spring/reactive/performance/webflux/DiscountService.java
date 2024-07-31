package com.baeldung.spring.reactive.performance.webflux;

import java.math.BigDecimal;

import reactor.core.publisher.Mono;

class DiscountService {
    public Mono<BigDecimal> discountForProduct(String productId) {
        return Mono.just(BigDecimal.valueOf(10));
    }
}