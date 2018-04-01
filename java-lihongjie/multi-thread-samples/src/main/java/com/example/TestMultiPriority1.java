package com.example;

public class TestMultiPriority1 extends Thread {

    public void run() {
        System.out.println("running thread name is:"+Thread.currentThread().getName());
        System.out.println("running thread priority is:"+Thread.currentThread().getPriority());
    }

    public static void main(String[] args) {
        TestMultiPriority1 m1 = new TestMultiPriority1();
        TestMultiPriority1 m2 = new TestMultiPriority1();

        m1.setPriority(Thread.MIN_PRIORITY);
        m2.setPriority(Thread.MAX_PRIORITY); // 优先级高

        m1.start();
        m2.start();
    }

    /**
     * output:
     * running thread name is:Thread-1
     * running thread priority is:10
     * running thread name is:Thread-0
     * running thread priority is:1
     */
}
