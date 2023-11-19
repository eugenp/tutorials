package com.baeldung.concurrent.virtualthreads.vs.webflux.webflux;

import java.math.BigDecimal;

import reactor.core.publisher.Mono;

class DiscountService {

    public Mono<BigDecimal> discountForProduct(String productId) {
        return Mono.just(BigDecimal.valueOf(10));
    }

}
