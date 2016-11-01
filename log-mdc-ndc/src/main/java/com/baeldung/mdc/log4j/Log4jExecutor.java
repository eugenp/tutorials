package com.baeldung.mdc.log4j;

import org.apache.log4j.MDC;

import com.baeldung.mdc.TransactionContext;
import com.baeldung.mdc.TransactionFactory;

public class Log4jExecutor implements Runnable {
	
	public void run() {

		TransactionContext ctx = new TransactionFactory().buildTransaction();

		MDC.put("transaction.id", ctx.getTransactionId());
		MDC.put("transaction.owner", ctx.getOwner());
		MDC.put("transaction.createdAt", ctx.getCreatedAt());

		new Log4jBusinessService().transfer(ctx.getAmount());
	}

}
