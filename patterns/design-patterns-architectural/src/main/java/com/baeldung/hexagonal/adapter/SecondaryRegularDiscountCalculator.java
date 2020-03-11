package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.DiscountCalculator;

public class SecondaryRegularDiscountCalculator implements DiscountCalculator {

    @Override
    public float applyDiscount(float price) {
        return price * .95F;
    }

}
