package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.DiscountCalculator;

public class SalesDiscountCalculator implements DiscountCalculator {

    @Override
    public float applyDiscount(float price) {
        return price * .75F;
    }

}
