package com.baeldung.concurrent.threadlifecycle;

public class BlockedState {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new DemoThreadB());
        Thread thread2 = new Thread(new DemoThreadB());
        
        thread1.start();
        thread2.start();
        
        Thread.sleep(1000);
        
        System.out.println(thread2.getState());
        System.exit(0);
    }
}

class DemoThreadB implements Runnable {
    @Override
    public void run() {
        commonResource();
    }
    
    public static synchronized void commonResource() {
        while(true) {
            // Infinite loop to mimic heavy processing
            // Thread 'thread1' won't leave this method
            // when Thread 'thread2' enters this
        }
    }
}