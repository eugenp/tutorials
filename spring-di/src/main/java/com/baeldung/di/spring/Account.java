package com.baeldung.di.spring;

import org.springframework.stereotype.Component;

@Component
public class Account {

	private String accountNumber;
	private String type;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
