package com.baeldung.mdc.logback;

import org.slf4j.MDC;

import com.baeldung.mdc.TransactionContext;

public class LogbackExecutor implements Runnable {

	public void run() {

		String transactionId = "" + Math.random();
		String owner = "owner" + Math.random();
		TransactionContext ctx = new TransactionContext(transactionId, owner);

		MDC.put("transaction.id", transactionId);
		MDC.put("transaction.owner", owner);
		MDC.put("transaction.createdAt", ctx.getCreatedAt().toString());

		new LogbackBusinessService().businessMethod(ctx.getTransactionId());
	}

}
