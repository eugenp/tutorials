package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.exception.*;
import com.stripe.exception.StripeException;

public interface PaymentService {
	int debit(Integer amount) throws PaymentException, StripeException;
}
