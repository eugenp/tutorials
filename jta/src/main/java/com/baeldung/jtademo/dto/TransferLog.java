package com.baeldung.jtademo.dto;

import java.math.BigDecimal;

public class TransferLog {
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;

    public TransferLog(String fromAccountId, String toAccountId, BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
