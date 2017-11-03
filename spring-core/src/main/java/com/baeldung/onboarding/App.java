package com.baeldung.onboarding;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Person p = (Person) context.getBean("person");
		System.out.println(p.getName().getLastName() + ", " + p.getName().getFirstName());
	}
}