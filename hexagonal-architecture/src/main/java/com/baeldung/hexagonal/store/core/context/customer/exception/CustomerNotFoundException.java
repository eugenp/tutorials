package com.baeldung.hexagonal.store.core.context.customer.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("Customer not found!");
    }
}
