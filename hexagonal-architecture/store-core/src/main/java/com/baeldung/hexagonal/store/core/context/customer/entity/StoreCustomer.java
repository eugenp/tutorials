package com.baeldung.hexagonal.store.core.context.customer.entity;

public interface StoreCustomer {
    boolean isNegativeBalanceAllowed();

    Double getBalance();
}
