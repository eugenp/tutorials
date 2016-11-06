package com.baeldung.mdc.log4j;

import org.apache.log4j.MDC;

import com.baeldung.mdc.Transaction;

public class Log4JRunnable implements Runnable {
	private final Transaction tx;

	public Log4JRunnable(Transaction tx) {
		this.tx = tx;
	}

	public void run() {

		MDC.put("transaction.id", tx.getTransactionId());
		MDC.put("transaction.owner", tx.getOwner());
		MDC.put("transaction.createdAt", tx.getCreatedAt());

		new Log4JBusinessService().transfer(tx.getAmount());

		MDC.clear();

	}
}