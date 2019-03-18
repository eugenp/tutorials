package com.baeldung.hexagonal.store.core.context.customer.entity;

public interface StoreCustomer {
    boolean isNegativeBalanceAllowed();

    void withdrawFunds(Double amount);

    Double getBalance();

    String getEmail();

    void topUpFunds(Double amount);

    boolean hasEnoughFunds(Double amount);
}
