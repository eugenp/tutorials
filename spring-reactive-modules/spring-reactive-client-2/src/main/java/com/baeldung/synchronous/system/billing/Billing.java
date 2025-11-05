package com.baeldung.synchronous.system.billing;

public class Billing {

    private Long customerId;
    private Double balance;

    private Billing() {
        super();
    }

    public Billing(Long customerId, Double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getBalance() {
        return balance;
    }

}
