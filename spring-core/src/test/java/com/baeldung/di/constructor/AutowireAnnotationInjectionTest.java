package com.baeldung.di.constructor;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.constructor.config.SpringAutowireConstructorContext;
import com.baeldung.di.constructor.model.autowire.Computer;

public class AutowireAnnotationInjectionTest {
	
	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new AnnotationConfigApplicationContext(SpringAutowireConstructorContext.class);
	}

	@Test
	public void getComputer_() {
		final Computer computer = applicationContext.getBean("computer", Computer.class);
		assertNotNull(computer);
	}

}
