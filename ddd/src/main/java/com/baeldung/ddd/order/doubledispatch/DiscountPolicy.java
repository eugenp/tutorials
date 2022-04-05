package com.baeldung.ddd.order.doubledispatch;

public interface DiscountPolicy {
    double discount(Order order);
}
