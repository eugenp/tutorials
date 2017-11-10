package com.baeldung.di.setter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.setter.config.SpringAutowireSetterContext;
import com.baeldung.di.setter.model.autowire.Computer;

public class AutowireAnnotationInjectionTest {
	
	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new AnnotationConfigApplicationContext(SpringAutowireSetterContext.class);
	}

	@Test
	public void getComputer_() {
		final Computer computer = applicationContext.getBean("computer", Computer.class);
		assertNotNull(computer);
	}

}
