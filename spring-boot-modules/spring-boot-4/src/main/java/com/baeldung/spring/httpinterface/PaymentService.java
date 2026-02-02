package com.baeldung.spring.httpinterface;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

interface PaymentService {

    @PostExchange("/payments")
    Payment sendPayment(@RequestBody Payment book);

    record Payment(long id, double amount, String status) {

    }
}
