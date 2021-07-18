package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.exception.PaymentException;
import com.baeldung.hexagonal.service.PaymentService;
import com.stripe.exception.StripeException;

public class BillingHandler {
	private final PaymentService paymentService;

	public BillingHandler(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public int pay(Order order) throws PaymentException, StripeException {
		return paymentService
				.debit(order.getItems().stream().mapToInt(item -> item.getAmount() * item.getQuantity()).sum());
	}

}
