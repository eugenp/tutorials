package com.baeldung.ddd.order.doubledispatch.visitor;

import com.baeldung.ddd.order.doubledispatch.Order;
import com.baeldung.ddd.order.doubledispatch.SpecialOrder;

public class HtmlOrderViewCreator implements OrderVisitor {
    
    private String html;
    
    public String getHtml() {
        return html;
    }

    @Override
    public void visit(Order order) {
        html = String.format("<p>Regular order total cost: %s</p>", order.totalCost());
    }

    @Override
    public void visit(SpecialOrder order) {
        html = String.format("<h1>Special Order</h1><p>total cost: %s</p>", order.totalCost());
    }

}
