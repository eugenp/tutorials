package com.baeldung.SpringBeanInjectionExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringBeanInjectionExampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		SpringApplication.run(SpringBeanInjectionExampleApplication.class, args);
//		System.out.println(context.getBeanDefinitionCount());
		System.out.println(context.getBean("baeldungDao"));
	}
}
