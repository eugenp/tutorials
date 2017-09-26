package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConstructorBeanInjectionExample {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		ConstructorBasedStorageManager storage = (ConstructorBasedStorageManager) context
				.getBean("storageManagerUsingConstructor");
		storage.store("Abhinav", "abhinav@xyz.com");

		((ConfigurableApplicationContext) context).close();
	}

}