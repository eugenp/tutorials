package com.baeldung.cannotinstantiate.sealed;

public final class CreditCardPayment extends Payment {
    private final String cardNumber;

    public CreditCardPayment(double amount) {
        this.amount = amount;
        this.cardNumber = "";
    }

    public CreditCardPayment(double amount, String cardNumber) {
        this.amount = amount;
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
