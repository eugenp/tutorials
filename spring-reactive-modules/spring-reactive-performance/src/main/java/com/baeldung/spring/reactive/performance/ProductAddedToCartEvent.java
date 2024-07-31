package com.baeldung.spring.reactive.performance;

import java.math.BigDecimal;

public record ProductAddedToCartEvent(String productId, BigDecimal price, String currency, String cartId) {
}