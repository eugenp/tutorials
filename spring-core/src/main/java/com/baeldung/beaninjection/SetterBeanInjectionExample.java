package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SetterBeanInjectionExample {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SetterBasedStorageManager manager = (SetterBasedStorageManager) context
				.getBean("storageManagerUsingSet");
		manager.store("Avi", "avi@xyz.com");

		((ConfigurableApplicationContext) context).close();
	}
}