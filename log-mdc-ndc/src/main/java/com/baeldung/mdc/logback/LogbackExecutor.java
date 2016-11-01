package com.baeldung.mdc.logback;

import org.slf4j.MDC;

import com.baeldung.mdc.TransactionContext;
import com.baeldung.mdc.TransactionFactory;

public class LogbackExecutor implements Runnable {

	public void run() {

		TransactionContext ctx = new TransactionFactory().buildTransaction();

		MDC.put("transaction.id", ctx.getTransactionId());
		MDC.put("transaction.owner", ctx.getOwner());
		MDC.put("transaction.createdAt", ctx.getCreatedAt().toString());

		new LogbackBusinessService().transfer(ctx.getAmount());
	}



}
