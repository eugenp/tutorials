package com.baeldung.dlt;

import java.math.BigDecimal;
import java.util.Currency;

class PaymentTestUtils {

    static Payment createPayment(String reference) {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(71));
        payment.setCurrency(Currency.getInstance("GBP"));
        payment.setReference(reference);
        return payment;
    }
}