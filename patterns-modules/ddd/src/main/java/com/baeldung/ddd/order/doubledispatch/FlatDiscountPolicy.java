package com.baeldung.ddd.order.doubledispatch;

public class FlatDiscountPolicy implements DiscountPolicy {
    @Override
    public double discount(Order order) {
        return 0.01;
    }
}
