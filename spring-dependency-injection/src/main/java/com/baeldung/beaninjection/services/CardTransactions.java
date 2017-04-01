package com.baeldung.beaninjection.services;

public class CardTransactions implements PaymentService {
	
	public void debit(String accountId, int amount){
		System.out.println("Debit the amount- " + amount + " from account- "+ accountId + " using CardPayments");
	}
	
	public void credit(String accountId, int amount){
		System.out.println("Credit the amount- " + amount + " to account- "+ accountId + " using Cardpayments");
	}
}
