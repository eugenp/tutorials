package com.baeldung.concurrent.threadlifecycle;

public class NewState implements Runnable {
    public static void main(String[] args) {
        Runnable runnable = new NewState();
        Thread t = new Thread(runnable);
        System.out.println(t.getState());
    }
    
    @Override
    public void run() {
        
    }
}