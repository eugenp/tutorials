package com.baeldung.beaninjection.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.services.PaymentService;

@Component
public class MyApplication {

	//field based dependency injection
	//@Autowired
	private PaymentService service;
	
//	constructor-based dependency injection	
//	@Autowired
//	public MyApplication(MessageService svc){
//		this.service=svc;
//	}
	
	@Autowired
	public void setService(PaymentService svc){
		this.service=svc;
	}
	
	public void processPayments(String accountId, int amount){
		 this.service.debit(accountId, amount);
	}
}
