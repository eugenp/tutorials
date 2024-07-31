package com.baeldung.concurrent.threadlifecycle;

public class RunnableState implements Runnable {
    public static void main(String[] args) {
        Runnable runnable = new NewState();
        Thread t = new Thread(runnable);
        t.start();
        System.out.println(t.getState());
    }
    
    @Override
    public void run() {
        
    }
}