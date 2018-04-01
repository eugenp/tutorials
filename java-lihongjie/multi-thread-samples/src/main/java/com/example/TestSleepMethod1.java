package com.example;

/**
 * Sleep method in java
 */
public class TestSleepMethod1 extends Thread {

    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.print(i);
        }
    }

    public static void main(String[] args) {
        TestSleepMethod1 t1 = new TestSleepMethod1();
        TestSleepMethod1 t2 = new TestSleepMethod1();

        t1.start();
        t2.start();
    }

    /**
     * output:
     * 11223344
     * 注释掉 sleep 方法后,output:
     * 12341234
     */
}
