package com.baeldung;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnimalApp {

	/** 
	 * @param args
	 */
	public static void main(String args[]) {
		// Code commented below is tightly coupled and is not an example of Dependency Injection
		//Animal animal = new Animal();
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		Animal animal = (Animal) context.getBean("animal");
		animal.greet();
	}
}
