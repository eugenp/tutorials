package com.baeldung.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {

    private Long id;
    private String uuid = UUID.randomUUID().toString();
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
