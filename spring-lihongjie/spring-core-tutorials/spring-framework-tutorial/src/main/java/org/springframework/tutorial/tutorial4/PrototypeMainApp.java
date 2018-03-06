package org.springframework.tutorial.tutorial4;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrototypeMainApp {
	
	public static void main(String[] args) throws IOException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("org/springframework/tutorial/tutorial4/beans.xml");
		
		HelloWorld helloA = (HelloWorld) context.getBean("myPrototype");
		
		helloA.getMessage();
		helloA.setMessage("Hello Spring!");
		
		HelloWorld helloB = (HelloWorld) context.getBean("myPrototype");
		
		helloB.getMessage();
		
		helloA.getMessage();
	}


}
