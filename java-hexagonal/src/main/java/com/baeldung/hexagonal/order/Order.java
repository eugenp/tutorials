package com.baeldung.hexagonal.order;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.List;

public class Order {
    private List<OrderLine> orderLines;
    private Money totalCost;

    public Order(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
        totalCost = Money.of(CurrencyUnit.USD, 0);
        orderLines.forEach(orderLine -> {
            totalCost = totalCost.plus(orderLine.getProduct()
                .getPrice());
        });
    }

    public Order() {
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Money getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Money totalCost) {
        this.totalCost = totalCost;
    }
}
