package com.baeldung.hexagonal.banking.application.service;

public class NotEnoughBalanceException extends RuntimeException {

    public NotEnoughBalanceException() {
        super("Not enough balance. Account cannot be overdraw.");
    }

}
