package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorld {
	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("/application-context.xml");
		SayGreeting sayGreeting = 
				(SayGreeting) applicationContext.getBean("sayGreeting");
		sayGreeting.doHelloWorld();
		
		SayGreeting sayGreetingSetter = 
				(SayGreeting) applicationContext.getBean("sayGreeting-setter");
		sayGreetingSetter.doHelloWorld();
		
		((ClassPathXmlApplicationContext) applicationContext).close();
	}
}
