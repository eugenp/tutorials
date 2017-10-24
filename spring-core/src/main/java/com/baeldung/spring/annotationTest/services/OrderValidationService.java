package com.baeldung.spring.annotationTest.services;

import org.springframework.stereotype.Component;

@Component
public class OrderValidationService {

	public void doOrderValidation()
	{
		System.out.println("Annotation test: Validating the orders");
	}
}
