package com.baeldung.ddd.order.doubledispatch.visitor;

import com.baeldung.ddd.order.doubledispatch.Order;
import com.baeldung.ddd.order.doubledispatch.SpecialOrder;

public interface OrderVisitor {
    void visit(Order order);
    void visit(SpecialOrder order);
}
