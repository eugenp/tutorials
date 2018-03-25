package com.baeldung.spring.core.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.spring.core.Customer;

public class AnnotationApplicationContextTest {

	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		Customer customer = applicationContext.getBean(Customer.class);
		System.out.println("Customer is :: " + customer);
		System.out.println("Product is :: " + customer.getProduct());
		applicationContext.close();
	}
}
