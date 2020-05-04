package com.baeldung.hexagonal.banking.application.service;

import java.math.BigDecimal;

public class InvalidDataException extends RuntimeException {
    
    public InvalidDataException(int pin, BigDecimal initialBalance) {
        super(String.format("Invalid data to open account: tried to use pin %s and initial balance %s.", pin, initialBalance.toString()));
    }

}
