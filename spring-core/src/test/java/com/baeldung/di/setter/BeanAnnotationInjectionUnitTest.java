package com.baeldung.di.setter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.setter.config.SpringBeanSetterContext;
import com.baeldung.di.setter.model.bean.Computer;

public class BeanAnnotationInjectionUnitTest {
	
	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new AnnotationConfigApplicationContext(SpringBeanSetterContext.class);
	}

	@Test
	public void getComputer_() {
		final Computer computer = applicationContext.getBean("computer", Computer.class);
		assertNotNull(computer);
	}

}
