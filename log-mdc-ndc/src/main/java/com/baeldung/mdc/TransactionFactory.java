package com.baeldung.mdc;

import static java.lang.Math.floor;
import static java.lang.Math.random;

import java.util.UUID;

public class TransactionFactory {

	private static final String[] NAMES = {"John", "Susan", "Marc", "Samantha"};
	
	public Transaction newInstance() {
		String transactionId = UUID.randomUUID().toString();
		String owner = NAMES[ (int) floor(random()*NAMES.length) ];
		long amount = (long) (random()*1500 + 500);
		Transaction tx = new Transaction(transactionId, owner, amount);
		return tx;
	}
	
}
