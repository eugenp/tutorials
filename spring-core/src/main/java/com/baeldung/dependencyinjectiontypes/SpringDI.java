package com.baeldung.dependencyinjections;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDI {

	 @SuppressWarnings("resource")
	 public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("diContext.xml");

		//Constructor Based DI
		MailBox mailBox = (MailBox) context.getBean("mailBox");
		System.out.println(mailBox.getMessage());

		//Setter Based DI
		TelephoneDirectory telephoneDirectory = (TelephoneDirectory) context.getBean("telephoneDirectory");
		telephoneDirectory.displayPhoneDetails();
	}

}
