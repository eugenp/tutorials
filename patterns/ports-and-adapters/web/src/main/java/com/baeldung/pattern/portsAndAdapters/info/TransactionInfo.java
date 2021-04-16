package com.baeldung.pattern.portsAndAdapters.info;


import com.baeldung.pattern.portsAndAdapters.core.model.Transaction;

import java.util.Date;

public class TransactionInfo {

    private String amount;
    private String description;

    public TransactionInfo(String amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public TransactionInfo() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Transaction toDomain(TransactionInfo info) {
        return new Transaction(info.getDescription(), Double.parseDouble(info.getAmount()), new Date());
    }
}
