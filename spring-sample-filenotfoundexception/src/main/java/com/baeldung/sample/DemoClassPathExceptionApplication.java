package com.baeldung.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DemoClassPathExceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoClassPathExceptionApplication.class, args);
		ApplicationContext context = new ClassPathXmlApplicationContext("main/resources/applicationContext.xml");
		//To fix the FileNotFoundException while reading the applicationContext file, modify the code as below
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
