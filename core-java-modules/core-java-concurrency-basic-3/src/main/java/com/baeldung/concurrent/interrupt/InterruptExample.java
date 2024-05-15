package com.baeldung.concurrent.interrupt;

public class InterruptExample extends Thread {
    
    public static void propagateException() throws InterruptedException {
        Thread.sleep(1000);
        Thread.currentThread().interrupt();
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public static Boolean restoreTheState() {
        InterruptExample thread1 = new InterruptExample();
        thread1.start();
        thread1.interrupt();
        return thread1.isInterrupted();
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static void throwCustomException() throws Exception {

        Thread.sleep(1000);
        Thread.currentThread().interrupt();
        if (Thread.interrupted()) {
            throw new CustomInterruptedException("This thread was interrupted");
        }
    }
    
    public static Boolean handleWithCustomException() throws CustomInterruptedException{
        try {
            Thread.sleep(1000);
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CustomInterruptedException("This thread was interrupted...");
        }
        return Thread.currentThread().isInterrupted();
    }
}