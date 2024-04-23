package org.ordermanagement.copy.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Order implements Cloneable {

    private final String id;
    private final String description;
    private final List<OrderLine> lines;

    public Order(String orderId, String description, List<OrderLine> lines) {
        this.id = orderId;
        this.description = description;
        this.lines = lines;
    }

    // Copy constructor demonstrates deep copy implementation
    public Order(Order other) {
        this.id = other.id;
        this.description = other.description;
        this.lines = new ArrayList<>();
        for (OrderLine line : other.lines) {
            this.lines.add(new OrderLine(line.getDescription(), line.getQuantity()));
        }
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        return (Order) super.clone();
    }

}
