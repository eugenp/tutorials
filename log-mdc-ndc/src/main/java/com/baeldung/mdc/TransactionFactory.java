package com.baeldung.mdc;

public class TransactionFactory {

	public TransactionContext buildTransaction() {
		TransactionContext ctx = new TransactionContext("" + Math.random(), "owner" + Math.random());
		return ctx;
	}
	
}
