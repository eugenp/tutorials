package com.baeldung.DIEval.DIEvaluation;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SetterInjectionCustomerTest {

	String COUNTRYNAME;
	AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		
		COUNTRYNAME="USA";
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.baeldung.DIEval.DIEvaluation");
		context.refresh();
	}

	@After
	public void tearDown() throws Exception {
		
		context.close();
	}

	@Test
	public final void testSetterInjectionCustomer() {
		SetterInjectionCustomer customer = context.getBean(SetterInjectionCustomer.class);
		String countryName = customer.getCustCountry("101");
		
		assertTrue(COUNTRYNAME.equals(countryName));
	}


}

