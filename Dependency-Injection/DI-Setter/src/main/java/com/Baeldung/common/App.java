package com.Baeldung.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Baeldung.output.OutputHelper;

public class App {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"SpringBeans.xml");

		OutputHelper output = (OutputHelper)context.getBean("setterHelper");
    	output.generateOutput();
	}
}