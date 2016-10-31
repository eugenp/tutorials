package com.baeldung.mdc.log4j2;

import org.apache.logging.log4j.ThreadContext;

import com.baeldung.mdc.TransactionContext;

public class Log4j2Executor implements Runnable {
	
	public void run() {

		String transactionId = "" + Math.random();
		String owner = "owner" + Math.random();
		TransactionContext ctx = new TransactionContext(transactionId, owner);
		
		ThreadContext.put("transaction.id", transactionId);
		ThreadContext.put("transaction.owner", owner);
		ThreadContext.put("transaction.createdAt", ctx.getCreatedAt().toString());

		new Log4j2BusinessService().businessMethod(ctx.getTransactionId());
	}

}
