package com.baeldung.concurrent.threadlifecycle;

public class WaitingState implements Runnable {
    public static Thread thread1;

    public static void main(String[] args) {
        thread1 = new Thread(new WaitingState());
        thread1.start();
    }

    public void run() {
        Thread thread2 = new Thread(new DemoThreadWS());
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class DemoThreadWS implements Runnable {
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(WaitingState.thread1.getState());
    }
}