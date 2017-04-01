package com.baeldung.beaninjection.consumer;

import com.baeldung.beaninjection.services.PaymentService;

public class MyXMLApplication {

	private PaymentService service;

	//constructor-based dependency injection
//	public MyXMLApplication(MessageService svc) {
//		this.service = svc;
//	}
	
	//setter-based dependency injection
	public void setService(PaymentService svc){
		this.service=svc;
	}

	public void processPayments(String accountId, int amount) {
		// some magic like validation, logging etc
		this.service.debit(accountId, amount);
	}
}
