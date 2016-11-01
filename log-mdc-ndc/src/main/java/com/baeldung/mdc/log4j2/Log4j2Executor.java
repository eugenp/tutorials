package com.baeldung.mdc.log4j2;

import org.apache.logging.log4j.ThreadContext;

import com.baeldung.mdc.TransactionContext;
import com.baeldung.mdc.TransactionFactory;

public class Log4j2Executor implements Runnable {
	
	public void run() {

		TransactionContext ctx = new TransactionFactory().buildTransaction();
		
		ThreadContext.put("transaction.id", ctx.getTransactionId());
		ThreadContext.put("transaction.owner", ctx.getOwner());
		ThreadContext.put("transaction.createdAt", ctx.getCreatedAt().toString());

		new Log4j2BusinessService().transfer(ctx.getAmount());
	}

}
