package com.baeldung.hexagonal.store.core.context.order.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @Column(nullable = false)
    private Integer quantity;

    public OrderProduct() {
        super();
    }

    public OrderProduct(Order order, Product product, Integer quantity) {
        id = new OrderProductId();
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
    }

    @Transient
    public Product getProduct() {
        return this.id.getProduct();
    }

    @Transient
    public Double getTotalPrice() {
        return getProduct().getPrice() * getQuantity();
    }

    public Integer getQuantity() {
        return quantity;
    }
}
