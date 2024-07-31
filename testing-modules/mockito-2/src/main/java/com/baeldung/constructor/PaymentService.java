package com.baeldung.constructor;

public class PaymentService {

    private final String paymentMode;

    public PaymentService(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentService() {
        this.paymentMode = "Cash";
    }

    public String processPayment(){
        // simulate processing payment and returns the payment mode
        return this.paymentMode;
    }
}
