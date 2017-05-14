package com.baeldung.beaninjection.services;

public interface PaymentService {

	public void debit(String accountId, int amount);
	
	public void credit(String accountId, int amount);
	
}
