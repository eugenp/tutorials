package com.baeldung.jpa.hibernateunproxy;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CreditCardPayment extends Payment {

    private String cardNumber;

    CreditCardPayment(BigDecimal amount, WebUser webUser, String cardNumber) {
        this.amount = amount;
        this.webUser = webUser;
        this.cardNumber = cardNumber;
    }

    protected CreditCardPayment() {
    }

}
