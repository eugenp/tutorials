package com.baeldung.threadparking;

import java.util.concurrent.locks.LockSupport;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        final Object syncObj = new Object();
        Thread t = new Thread(() -> {
            int acc = 0;
            for (int i = 1; i <= 100; i++) {
                acc += i;
            }
            System.out.println("Work finished");
            LockSupport.park(syncObj);
            System.out.println(acc);
        });
        t.setName("PARK-THREAD");
        t.start();

//        Thread.sleep(1000);
        LockSupport.unpark(t);
    }

}
