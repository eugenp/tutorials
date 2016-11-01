package com.baeldung.mdc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.MDC;

import com.baeldung.mdc.log4j.Log4jBusinessService;

public class GenericApp {
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		TransactionFactory transactionFactory = new TransactionFactory();
		
		for(int i=0; i<10; i++){
			
			TransactionContext tx = transactionFactory.buildTransaction();
			
			executor.submit(new Runnable() {
				@Override
				public void run() {
					new DelegateBusiness() {
						
						@Override
						protected void preTransfer() {
							MDC.put("transaction.id", tx.getTransactionId());
							MDC.put("transaction.owner", tx.getOwner());
							MDC.put("transaction.createdAt", tx.getCreatedAt());
						}
						
						@Override
						protected void postTransfer(boolean outcome) {
							// TODO Auto-generated method stub
							
						}
					}.transfer(
							tx.getAmount()
					);
				}
			});
		}
		
	}
}
