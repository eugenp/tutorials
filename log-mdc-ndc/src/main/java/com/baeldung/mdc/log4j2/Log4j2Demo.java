package com.baeldung.mdc.log4j2;

public class Log4j2Demo {
	
	public static void main(String[] args) throws InterruptedException {
		Log4j2Executor greeter = new Log4j2Executor();
		Thread thread1 = new Thread(greeter);
		Thread thread2 = new Thread(greeter);
		
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
	}

}
