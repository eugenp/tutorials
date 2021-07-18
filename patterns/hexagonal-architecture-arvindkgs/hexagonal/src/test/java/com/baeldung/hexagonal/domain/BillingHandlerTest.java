package com.baeldung.hexagonal.domain;

import org.mockito.Mockito;
import org.junit.Assert;

import com.baeldung.hexagonal.exception.PaymentException;
import com.baeldung.hexagonal.service.PaymentService;
import com.stripe.exception.StripeException;

public class BillingHandlerTest {
	public void payTest() throws PaymentException, StripeException {
		PaymentService mockPay = Mockito.mock(PaymentService.class);
		Mockito.when(mockPay.debit(Mockito.any())).then(x-> 100);
		BillingHandler billing = new BillingHandler(mockPay);
		Order singleOrder = new Order();
		singleOrder.getItems().add(new Item(100,1));
		Assert.assertEquals(100, billing.pay(singleOrder));
	}

}
