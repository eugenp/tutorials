package com.baeldung.di.spring;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInjectionTest {

	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("com.baeldung.di.spring.xml");
	}

	@Test
	public void protoBean_getBean_returnsMultipleInstance() {
		final MessageApp messageApp1 = applicationContext.getBean("messageWorldApp", MessageApp.class);
		final MessageApp messageApp2 = applicationContext.getBean("messageWorldApp", MessageApp.class);
		assertNotEquals(messageApp1, messageApp2);
	}

	@Test
	public void protoFactoryMethod_getBean_returnsMultipleInstance() {
		final IndexApp indexApp1 = applicationContext.getBean("indexAppWithFactoryMethod", IndexApp.class);
		final IndexApp indexApp2 = applicationContext.getBean("indexAppWithFactoryMethod", IndexApp.class);
		assertNotEquals(indexApp1, indexApp2);
	}

	@Test
	public void protoStaticFactory_getBean_returnsMultipleInstance() {
		final IndexApp indexApp1 = applicationContext.getBean("indexAppWithStaticFactory", IndexApp.class);
		final IndexApp indexApp2 = applicationContext.getBean("indexAppWithStaticFactory", IndexApp.class);
		assertNotEquals(indexApp1, indexApp2);
	}

	@Test
	public void singletonBean_getBean_returnsSingleInstance() {
		final IndexApp indexApp1 = applicationContext.getBean("indexApp", IndexApp.class);
		final IndexApp indexApp2 = applicationContext.getBean("indexApp", IndexApp.class);
		assertEquals(indexApp1, indexApp2);
	}
}
