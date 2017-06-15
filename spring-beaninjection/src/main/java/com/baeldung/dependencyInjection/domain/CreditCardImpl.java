package com.baeldung.dependencyInjection.domain;

import org.springframework.stereotype.Component;

@Component
public class CreditCardImpl implements CreditCard {

    @Override
    public boolean validateCreditCard() {
        return true;
    }
}
