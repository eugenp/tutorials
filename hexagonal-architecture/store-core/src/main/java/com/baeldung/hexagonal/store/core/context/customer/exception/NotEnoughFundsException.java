package com.baeldung.hexagonal.store.core.context.customer.exception;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException() {
        super("Not enough funds!");
    }
}