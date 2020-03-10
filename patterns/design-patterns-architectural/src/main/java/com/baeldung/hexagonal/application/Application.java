package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.port.PriceCalculator;
import com.baeldung.hexagonal.port.DiscountCalculator;

public class Application implements PriceCalculator {
    private final DiscountCalculator provider;

    public Application(DiscountCalculator provider) {
        this.provider = provider;
    }

    @Override
    public float calculateFinalPrice(float shoppingCartPrice) {
        float priceBeforeShipping = this.provider.applyDiscount(shoppingCartPrice);
        float finalPrice = priceBeforeShipping < 40F ? priceBeforeShipping + 3.99F : priceBeforeShipping;

        return finalPrice;
    }
}
