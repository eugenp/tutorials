package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;

public class Order {
    private Long id;
    private BigDecimal price;

    public Order() {
    }

    public Order(Long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
