package com.baeldung.webflux.onsuccess;

import com.baeldung.webflux.model.Payment;

public class PaymentService {

    public void processPayment(Payment payment) {
        System.err.println("Payment processed: " + payment);
    }

}