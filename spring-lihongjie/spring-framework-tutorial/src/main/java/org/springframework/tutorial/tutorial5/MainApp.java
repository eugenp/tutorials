package org.springframework.tutorial.tutorial5;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("org/springframework/tutorial/tutorial5/beans.xml");
		
//		HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
//		obj.getMessage();
//		context.registerShutdownHook();
//		
//		//
//		
//		HelloWorld1 helloWorld1 = (HelloWorld1) context.getBean("helloWorld1");
//		helloWorld1.getMessage();
		
		HelloWorld2 obj2 = (HelloWorld2) context.getBean("helloWorld2");
		obj2.getMessage();
		context.registerShutdownHook();
		
	}
}
