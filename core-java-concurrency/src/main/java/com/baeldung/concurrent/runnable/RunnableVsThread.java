package com.baeldung.concurrent.runnable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableVsThread {

	private static Logger log = 
	  LoggerFactory.getLogger(RunnableVsThread.class);

	@Test
	public void givenARunnable_whenRunIt_thenResult() throws Exception{
		Thread thread = new Thread(new SimpleRunnable(
		  "SimpleRunnable executed using Thread"));
		thread.start();
		thread.join();
		
		ExecutorService executorService = 
		  Executors.newCachedThreadPool();
		
		executorService.submit(new SimpleRunnable(
		  "SimpleRunnable executed using ExecutorService"));
		
		executorService.submit(()-> 
		  log.info("Lambda runnable executed!!!"));
		executorService.shutdown();
	}
	
	@Test
	public void givenAThread_whenRunIt_thenResult() throws Exception{
		Thread thread = new SimpleThread(
		  "SimpleThread executed using Thread");
		thread.start();
		thread.join();
		
		ExecutorService executorService = 
		  Executors.newCachedThreadPool();
		executorService.submit(new SimpleThread(
		  "SimpleThread executed using ExecutorService"));
	}
	
	@Test
	public void givenACallable_whenRunIt_thenResult() throws Exception {
		ExecutorService executorService = 
		  Executors.newCachedThreadPool();
		
		Future<Integer> future = executorService.submit(new SimpleCallable());
		log.info("Result from callable: {}", future.get());
		
		future = executorService.submit(() -> {
		  return RandomUtils.nextInt(0, 100);
		}); 
		log.info("Result from callable: {}", future.get());

	}
}

class SimpleThread extends Thread{
	
	private static final Logger log = 
	  LoggerFactory.getLogger(SimpleThread.class);
	
	private String message;
	
	public SimpleThread(String message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		log.info(message);
	}
}

class SimpleRunnable implements Runnable {

	private static final Logger log = 
	  LoggerFactory.getLogger(SimpleRunnable.class);
	
	private String message;
	
	public SimpleRunnable(String message) {
		this.message = message;
	}
	
	
	@Override
	public void run() {
		log.info(message);
	}
}

class SimpleCallable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return RandomUtils.nextInt(0, 100);
	}

}