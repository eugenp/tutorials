package com.baeldung.paymentservice;

public interface PaymentClient {

    PaymentResponse pay (String orderNumber, PaymentDTO paymentDTO);
}
