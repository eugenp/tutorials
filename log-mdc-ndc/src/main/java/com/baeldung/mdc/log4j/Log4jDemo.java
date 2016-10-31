package com.baeldung.mdc.log4j;

public class Log4jDemo {
	
	public static void main(String[] args) throws InterruptedException {
		Log4jExecutor greeter = new Log4jExecutor();
		Thread thread1 = new Thread(greeter);
		Thread thread2 = new Thread(greeter);
		
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
	}

}
