package com.baeldung.cloud.openfeign.controller;

import com.baeldung.cloud.openfeign.client.PaymentClient;
import com.baeldung.cloud.openfeign.model.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentClient paymentClient;

    public PaymentController(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        List<Payment> payments = paymentClient.getPayments();
        return payments;
    }
}
