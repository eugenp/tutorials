package com.baeldung.mdc;

public class Transaction {

    private String transactionId;
    private String owner;
    private Long amount;

    public Transaction(String transactionId, String owner, long amount) {
        this.transactionId = transactionId;
        this.owner = owner;
        this.amount = amount;
    }

    public String getOwner() {
        return owner;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Long getAmount() {
        return amount;
    }

}
