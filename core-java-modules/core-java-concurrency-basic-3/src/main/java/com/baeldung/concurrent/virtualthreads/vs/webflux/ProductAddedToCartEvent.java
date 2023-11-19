package com.baeldung.concurrent.virtualthreads.vs.webflux;

import java.math.BigDecimal;

public record ProductAddedToCartEvent (String productId, BigDecimal price, String currency, String cartId) {
}
