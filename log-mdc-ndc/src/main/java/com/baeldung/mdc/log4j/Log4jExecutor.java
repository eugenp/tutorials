package com.baeldung.mdc.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.baeldung.mdc.TransactionContext;

public class Log4jExecutor implements Runnable {
	
	public void run() {

		String transactionId = "" + Math.random();
		String owner = "owner" + Math.random();
		TransactionContext ctx = new TransactionContext(transactionId, owner);

		MDC.put("transaction.id", transactionId);
		MDC.put("transaction.owner", owner);
		MDC.put("transaction.createdAt", ctx.getCreatedAt());

		new Log4jBusinessService().businessMethod(ctx.getTransactionId());
	}

}
