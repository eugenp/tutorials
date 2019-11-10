package com.baeldung.payment.service.impl;


import org.springframework.stereotype.Repository;

import com.baeldung.payment.domain.Payment;
import com.baeldung.payment.service.PaymentExternalAdapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
// Secondary adapter
public class PaymentExternalAdapterImpl implements PaymentExternalAdapter {
	@Override
	public Flux<Payment> getPaymentInformation(Integer paymentId) {
		//Here you can make DB call or external API call to get data.
		Payment payment=new Payment();
		payment.setAddress("test address");
		payment.setAmount(10.01);
		payment.setId(1234567);
		payment.setMerchantCode("Abc1234");
		payment.setMerchantName("Test Merchant");
		payment.setPostalCode(12345);
		return Flux.just(payment);
	}

	@Override
	public Mono<Boolean> makePayment(Payment request) {
		//Here you can make DB call or external API call to post payment
		Boolean makePayment=Boolean.FALSE;
		if(request!=null && request.getMerchantCode()!=null)
		{
			makePayment=Boolean.TRUE;
		}
		return Mono.just(makePayment);
	}

}
