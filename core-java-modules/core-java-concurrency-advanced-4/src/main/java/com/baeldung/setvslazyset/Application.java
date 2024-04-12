package com.baeldung.setvslazyset;

import java.util.concurrent.atomic.AtomicInteger;

public class Application {

    AtomicInteger atomic = new AtomicInteger(0);

    public static void main(String[] args) {
        Application app = new Application();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                //app.atomic.set(i);
                app.atomic.lazySet(i);
                System.out.println("Set: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (app.atomic) {
                    int counter = app.atomic.get();
                    System.out.println("Get: " + counter);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

}
