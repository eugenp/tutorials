package com.baeldung.ddd.order.doubledispatch;

public interface SpecialDiscountPolicy extends DiscountPolicy {
    double discount(SpecialOrder order);
}
