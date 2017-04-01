package com.baeldung.beaninjection.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.services.CardTransactions;
import com.baeldung.beaninjection.services.PaymentService;

@Configuration
@ComponentScan(value={"com.baeldung.beaninjection.consumer"})
public class DIConfiguration {

	@Bean
	public PaymentService getMessageService(){
		return new CardTransactions();
	}
}
