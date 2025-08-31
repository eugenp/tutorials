package com.baeldung.ruleengine;

import com.baeldung.ruleengine.model.Order;

public class LoyaltyDiscountRule implements IRule{

    @Override
    public boolean evaluate(Order order) {
        return order.getCustomer().getLoyaltyPoints() > 500;
    }

    @Override
    public String description() {
        return "Loyalty Discount Rule: Customer has more than 500 points";
    }
}
