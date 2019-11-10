package com.baeldung.payment.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.payment.domain.Payment;
import com.baeldung.payment.service.PaymentExternalAdapter;
import com.baeldung.payment.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentExternalAdapter adapter;
	
	@Override
	public Flux<Payment>getPaymentInformation(Integer paymentId) {
		return adapter.getPaymentInformation(paymentId);
	}
	@Override
	public Mono<Boolean> makePayment(Payment request) {
		return adapter.makePayment(request);
	}


}
