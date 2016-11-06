package com.baeldung.mdc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.baeldung.mdc.log4j.Log4JRunnable;

public class GenericApp {
		
	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		TransactionFactory transactionFactory = new TransactionFactory();
		
		for(int i=0; i<10; i++){
			final Transaction tx = transactionFactory.newInstance();
			executor.submit(new Log4JRunnable(tx));
		}
		
		executor.shutdown();
				
	}
}
