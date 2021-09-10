package com.example.hexagonalarchitecture.kitchenassistant.application.service;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Dear user your request cannot be processed because your wallet balance is insufficient kindly top up");
    }

}