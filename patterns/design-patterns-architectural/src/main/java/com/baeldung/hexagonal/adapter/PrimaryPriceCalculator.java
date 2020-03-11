package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.PriceCalculator;
import com.baeldung.hexagonal.port.DiscountCalculator;

public class PrimaryPriceCalculator implements PriceCalculator {
    private final DiscountCalculator provider;

    public PrimaryPriceCalculator(DiscountCalculator provider) {
        this.provider = provider;
    }

    @Override
    public float calculateFinalPrice(float shoppingCartPrice) {
        float priceBeforeShipping = this.provider.applyDiscount(shoppingCartPrice);
        float finalPrice = priceBeforeShipping < 40F ? priceBeforeShipping + 3.99F : priceBeforeShipping;

        return finalPrice;
    }
}
