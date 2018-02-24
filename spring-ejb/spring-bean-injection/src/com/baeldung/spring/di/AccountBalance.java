package com.baeldung.spring.di;

import org.springframework.stereotype.Component;

@Component
class AccountBalance {
	private double balance = 200;

	public double getBalance() {
		return balance;
	}
}
