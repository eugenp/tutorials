package com.baeldung.ddd.order.doubledispatch;

import java.math.RoundingMode;
import java.util.List;

import org.joda.money.Money;

import com.baeldung.ddd.order.OrderLine;
import com.baeldung.ddd.order.doubledispatch.visitor.OrderVisitor;
import com.baeldung.ddd.order.doubledispatch.visitor.Visitable;

public class Order extends com.baeldung.ddd.order.Order implements Visitable<OrderVisitor> {
    public Order(List<OrderLine> orderLines) {
        super(orderLines);        
    }

    public Money totalCost(SpecialDiscountPolicy discountPolicy) {
        return totalCost().multipliedBy(1 - applyDiscountPolicy(discountPolicy), RoundingMode.HALF_UP);
    }

    protected double applyDiscountPolicy(SpecialDiscountPolicy discountPolicy) {
        return discountPolicy.discount(this);
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);        
    }
}
