package com.baeldung.cannotinstantiate.sealed;

public abstract sealed class Payment permits CreditCardPayment, BankTransfer {
    protected double amount;
}
