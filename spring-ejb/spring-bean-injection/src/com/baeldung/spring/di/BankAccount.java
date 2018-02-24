package com.baeldung.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class BankAccount {

	private AccountBalance accountBalance;

	@Autowired
	public void setAccountBalance(AccountBalance accountBalance) {
		this.accountBalance = accountBalance;
	}

	public void showBalance() {
		System.out.println(accountBalance.getBalance());
	}
}