package com.baeldung.payment.service;


import com.baeldung.payment.domain.Payment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
// Inbound port 
public interface PaymentService {
	
	public Mono<Boolean> makePayment(Payment request);

	public Flux<Payment> getPaymentInformation(Integer paymentId);

}
