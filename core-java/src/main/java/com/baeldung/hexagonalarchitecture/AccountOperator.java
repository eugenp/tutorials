package com.baeldung.hexagonalarchitecture;

public interface AccountOperator {
	public int debitAccount(Double amount, String accountDetails, String transactionDetails);
	
	public double checkBalance(String accountDetails);
}
