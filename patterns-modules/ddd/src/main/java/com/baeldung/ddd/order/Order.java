package com.baeldung.ddd.order;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

public class Order {
    private final List<OrderLine> orderLines;
    private Money totalCost;

    public Order(List<OrderLine> orderLines) {
        checkNotNull(orderLines);
        if (orderLines.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one order line item");
        }
        this.orderLines = new ArrayList<>(orderLines);
        totalCost = calculateTotalCost();
    }

    public void addLineItem(OrderLine orderLine) {
        checkNotNull(orderLine);
        orderLines.add(orderLine);
        totalCost = totalCost.plus(orderLine.cost());
    }

    public List<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

    public void removeLineItem(int line) {
        OrderLine removedLine = orderLines.remove(line);
        totalCost = totalCost.minus(removedLine.cost());
    }

    public Money totalCost() {
        return totalCost;
    }

    private Money calculateTotalCost() {
        return orderLines.stream()
            .map(OrderLine::cost)
            .reduce(Money::plus)
            .get();
    }

    private static void checkNotNull(Object par) {
        if (par == null) {
            throw new NullPointerException("Parameter cannot be null");
        }
    }
}
