package com.baeldung.exceptionhandling;

public interface PaymentService {

    void validatePaymentDetails(String paymentDetails);

    void processPayment(String paymentDetails);
}
