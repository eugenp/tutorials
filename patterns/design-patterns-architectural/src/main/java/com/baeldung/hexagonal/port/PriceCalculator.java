package com.baeldung.hexagonal.port;

public interface PriceCalculator {
    float calculateFinalPrice(float shoppingCartPrice);
}
