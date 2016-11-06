package com.baeldung.mdc.slf4j;

import org.slf4j.MDC;

import com.baeldung.mdc.Transaction;

final class Slf4jRunnable implements Runnable {
	private final Transaction tx;

	Slf4jRunnable(Transaction tx) {
		this.tx = tx;
	}

	public void run() {
							
		MDC.put("transaction.id", tx.getTransactionId());
		MDC.put("transaction.owner", tx.getOwner());
		MDC.put("transaction.createdAt", tx.getCreatedAt().toString());		
		
		new Slf4jBusinessService().transfer(tx.getAmount());
		
		MDC.clear();
							
	}
}