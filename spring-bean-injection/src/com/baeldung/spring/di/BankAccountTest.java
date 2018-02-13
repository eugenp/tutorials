package com.baeldung.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BankAccountTest {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		BankAccount account = (BankAccount) context.getBean("bankAccount");
		account.showBalance();
	}

}
