package com.baeldung.evaluation.di.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
	private IPayment payment;
	private Details details;

	@Autowired
	public PaymentService(IPayment payment) {
		this.payment = payment;
	}

	@Autowired
	public void setDetails(Details details) {
		this.details = details;
	}

	public void executePayment(int amount) {
		this.payment.pay(amount);
		System.out.println(this.details);
	}
}
