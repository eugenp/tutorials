package com.baeldung.di.setter;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.di.setter.model.xml.Computer;

public class XmlInjectionTest {
	
	 private ApplicationContext applicationContext;

	    @Before
	    public void setUp() throws Exception {
	        applicationContext = new ClassPathXmlApplicationContext("application-di-setter-context.xml");
	    }

	    @Test
	    public void getComputer_() {
	        final Computer computer = applicationContext.getBean("computer", Computer.class);
	        assertNotNull(computer);
	    }

}
