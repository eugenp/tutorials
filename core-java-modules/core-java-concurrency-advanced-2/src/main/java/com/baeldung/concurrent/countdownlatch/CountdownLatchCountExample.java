package com.baeldung.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchCountExample {

    private int count;

    public CountdownLatchCountExample(int count) {
        this.count = count;
    }

    public boolean callTwiceInSameThread() {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Thread t = new Thread(() -> {
            countDownLatch.countDown();
            countDownLatch.countDown();
        });
        t.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return countDownLatch.getCount() == 0;
    }

    public static void main(String[] args) {
        CountdownLatchCountExample ex = new CountdownLatchCountExample(2);
        System.out.println("Is CountDown Completed : " + ex.callTwiceInSameThread());
    }
}
