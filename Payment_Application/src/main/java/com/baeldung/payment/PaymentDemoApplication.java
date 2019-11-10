package com.baeldung.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.payment"})
public class PaymentDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentDemoApplication.class, args);
	}
}
