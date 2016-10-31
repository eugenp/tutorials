package com.baeldung.mdc.logback;

public class LogbackDemo {
	
	public static void main(String[] args) throws InterruptedException {
		LogbackExecutor greeter = new LogbackExecutor();
		Thread thread1 = new Thread(greeter);
		Thread thread2 = new Thread(greeter);
		
		thread1.start();
		thread2.start();
		
		thread1.join();
		thread2.join();
	}

}
