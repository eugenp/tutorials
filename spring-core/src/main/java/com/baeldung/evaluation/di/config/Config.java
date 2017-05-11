package com.baeldung.evaluation.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.evaluation.di.example.CashPayment;
import com.baeldung.evaluation.di.example.Details;
import com.baeldung.evaluation.di.example.IPayment;

@Configuration
@ComponentScan("com.baeldung.evaluation.di.example")
public class Config {
	@Bean
	public IPayment payment() {
		return new CashPayment();
	}

	@Bean
	public Details details() {
		return new Details("Installment");
	}
}
