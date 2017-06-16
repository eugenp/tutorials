package com.baeldung.dependencyInjection.domain;

import org.springframework.stereotype.Component;

@Component
public interface CreditCard {

    public boolean validateCreditCard();
}
