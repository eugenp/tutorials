package com.baeldung.hexagonalarchitecture.domain;

import com.baeldung.hexagonalarchitecture.port.OrderStatusChangedNotifier;

public class Order {

    private String id;
    private Customer customer;
    private OrderStatus status;

    private OrderStatusChangedNotifier orderStatusChangedNotifier;

    public void changeStatus(OrderStatus status) {
        this.status = status;
        orderStatusChangedNotifier.orderStatusChanged(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public static class Builder {

        private String id;
        private Customer customer;
        private OrderStatus status;

        private OrderStatusChangedNotifier orderStatusChangedNotifier;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder withStatus(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder withOrderStatusChangeNotifier(OrderStatusChangedNotifier orderStatusChangeNotifier) {
            this.orderStatusChangedNotifier = orderStatusChangeNotifier;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.id = id;
            order.customer = customer;
            order.status = status;
            order.orderStatusChangedNotifier = orderStatusChangedNotifier;
            return order;
        }
    }
}
