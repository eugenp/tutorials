package org.springframework.tutorial.tutorial4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SingletonMainApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("org/springframework/tutorial/tutorial4/beans.xml");
		
		HelloWorld helloA = (HelloWorld) context.getBean("helloWorld");
		
		helloA.getMessage();
		helloA.setMessage("Hello Spring!");
		
		HelloWorld helloB = (HelloWorld) context.getBean("helloWorld");
		helloB.getMessage();
		
	}
}
