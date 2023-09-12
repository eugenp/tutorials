package com.baeldung.constructor;

public class PaymentProcessor {

    private final PaymentService paymentService;

    public PaymentProcessor(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentProcessor() {
        this.paymentService = new PaymentService();
    }

    public PaymentProcessor(String paymentMode) {
        this.paymentService = new PaymentService(paymentMode);
    }

    public String processPayment(){
        return paymentService.processPayment();
    }
}
