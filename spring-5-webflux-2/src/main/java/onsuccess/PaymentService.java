package onsuccess;

import model.Payment;

public class PaymentService {

    public void processPayment(Payment payment) {
        System.err.println("Payment processed: " + payment);
    }

}