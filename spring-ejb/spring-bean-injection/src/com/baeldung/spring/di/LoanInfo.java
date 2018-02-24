package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanInfo {
	private BankAccount account;

	@Autowired
	public void setBankAccount(BankAccount account) {
		this.account = account;
	}

	public BankAccount getAccount() {
		return account;
	}
}
