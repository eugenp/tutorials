package com.baeldung.hexagonal.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {

    private final BigDecimal amount;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
