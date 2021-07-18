package com.baeldung.hexagonal.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.baeldung.hexagonal.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public class StripePaymentServiceimpl implements PaymentService {

	StripePaymentServiceimpl(@Value("${stripe.secret}") String stripeApiKey) {
		Stripe.apiKey = stripeApiKey;
	}

	@Override
	public int debit(Integer amount) throws PaymentException, StripeException {
		Map params = new HashMap<String, Object>();
		params.put("amount", amount);
		PaymentIntent debitObject = PaymentIntent.create(params);
		return debitObject.getAmount().intValue();
	}

}
