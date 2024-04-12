package com.baeldung.ndc;

public class Investment {
    private String transactionId;
    private String owner;
    private Long amount;

    public Investment() {
    }

    public Investment(String transactionId, String owner, Long amount) {
        this.transactionId = transactionId;
        this.owner = owner;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
