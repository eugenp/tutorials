package com.baeldung.concurrent.threadlifecycle;

public class BlockedState {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new DemoBlockedRunnable());
        Thread t2 = new Thread(new DemoBlockedRunnable());
        
        t1.start();
        t2.start();
        
        Thread.sleep(1000);
        
        System.out.println(t2.getState());
        System.exit(0);
    }
}

class DemoBlockedRunnable implements Runnable {
    @Override
    public void run() {
        commonResource();
    }
    
    public static synchronized void commonResource() {
        while(true) {
            // Infinite loop to mimic heavy processing
            // Thread 't1' won't leave this method
            // when Thread 't2' enters this
        }
    }
}