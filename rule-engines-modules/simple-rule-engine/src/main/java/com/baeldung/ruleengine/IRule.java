package com.baeldung.ruleengine;

import com.baeldung.ruleengine.model.Order;

public interface IRule {
    boolean evaluate(Order order);
    String description();
}
