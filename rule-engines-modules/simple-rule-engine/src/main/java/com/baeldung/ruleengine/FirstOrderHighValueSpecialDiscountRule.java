package com.baeldung.ruleengine;

import com.baeldung.ruleengine.model.Order;

public class FirstOrderHighValueSpecialDiscountRule implements IRule {

    @Override
    public boolean evaluate(Order order) {
        return order.getCustomer()
            .isFirstOrder() && order.getAmount() > 500;
    }

    @Override
    public String description() {
        return "First Order Special Discount Rule: First Time customer with high value order";
    }
}
