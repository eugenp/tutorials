package com.baeldung.patterns.hexagonal.architecture.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class StockTrade implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TradeOrder orderA;

    @ManyToOne
    private TradeOrder orderB;

    private LocalDateTime date = LocalDateTime.now();

    private TradeStatus status = TradeStatus.CREATED;

    StockTrade() {
    }

    public StockTrade(TradeOrder orderA, TradeOrder orderB) {
        if (orderA == null || orderB == null)
            throw new IllegalStateException("orders cannot be null");

        this.orderA = orderA;
        this.orderB = orderB;
    }

    public boolean fulfill() {
        if (!orderA.matches(orderB))
            throw new IllegalStateException("both orders have to be eligible for trading");

        orderA.fulfill();
        orderB.fulfill();

        this.status = TradeStatus.FULFILLED;
        return true;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public TradeOrder getOrderB() {
        return orderB;
    }

    public TradeOrder getOrderA() {
        return orderA;
    }

    public Long getId() {
        return id;
    }
}
