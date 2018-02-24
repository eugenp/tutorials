package com.baeldung.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BankAccountTest {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(BankAccountConfig.class);
		BankAccount account = (BankAccount) context.getBean("bankAccount");
		account.showBalance();
		LoanInfo loanInfo = (LoanInfo) context.getBean("loanInfo");
		loanInfo.getAccount().showBalance();
	}

}
