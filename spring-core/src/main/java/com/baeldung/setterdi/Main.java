package com.baeldung.setterdi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("setter_bean.xml");
		Bean1 bean1 = (Bean1) context.getBean("bean1"); // Setter injection happens here.
	}

}