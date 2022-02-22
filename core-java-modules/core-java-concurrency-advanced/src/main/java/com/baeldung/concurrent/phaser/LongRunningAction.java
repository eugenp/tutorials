package com.baeldung.concurrent.phaser;

import java.util.concurrent.Phaser;

class LongRunningAction implements Runnable {
    private String threadName;
    private Phaser ph;

    LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        System.out.println("This is phase " + ph.getPhase());
        System.out.println("Thread " + threadName + " before long running action");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Thread " + threadName + " action completed and waiting for others");
        ph.arriveAndAwaitAdvance();
        System.out.println("Thread " + threadName + " proceeding in phase " + ph.getPhase());
        
        ph.arriveAndDeregister();
    }
}