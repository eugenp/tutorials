package com.baeldung.DIEval.DIEvaluation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.DIEval.DIEvaluation.ConstructorInjectionCustomer;

public class ConstructorInjectionCustomerTest {
	
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
		public final void testConstructorInjectionCustomer() {
			
			ConstructorInjectionCustomer customer =context.getBean(ConstructorInjectionCustomer.class);
			String countryName = customer.fetchAddress("101");
			
			assertTrue(COUNTRYNAME.equals(countryName));
		}

}

