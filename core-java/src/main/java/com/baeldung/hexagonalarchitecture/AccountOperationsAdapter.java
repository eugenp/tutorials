package com.baeldung.hexagonalarchitecture;

public class AccountOperationsAdapter implements AccountOperator {
	@Override
	public int debitAccount(Double amount, String accountDetails, String transactionDetails) {
		System.out.println("Account debited for amount : "+amount);
		
		return 1;
	}

	@Override
	public double checkBalance(String accountDetails) {
		double balance = 15d;
		System.out.println("Account balance amount : "+balance);
		
		return balance;
	}
}
