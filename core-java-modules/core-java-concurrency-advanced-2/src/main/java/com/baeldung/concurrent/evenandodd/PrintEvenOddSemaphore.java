package com.baeldung.concurrent.evenandodd;

import java.util.concurrent.Semaphore;

public class PrintEvenOddSemaphore {

    public static void main(String[] args) {
        SharedPrinter sp = new SharedPrinter();
        Thread odd = new Thread(new Odd(sp, 10), "Odd");
        Thread even = new Thread(new Even(sp, 10), "Even");

        odd.start();
        even.start();
    }
}

class SharedPrinter {

    private final Semaphore semEven = new Semaphore(0);
    private final Semaphore semOdd = new Semaphore(1);

    void printEvenNum(int num) {
        try {
            semEven.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ":"+num);
        semOdd.release();
    }

    void printOddNum(int num) {
        try {
            semOdd.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ":"+ num);
        semEven.release();
    }
}

class Even implements Runnable {
    private final SharedPrinter sp;
    private final int max;

    Even(SharedPrinter sp, int max) {
        this.sp = sp;
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = 2; i <= max; i = i + 2) {
            sp.printEvenNum(i);
        }
    }
}

class Odd implements Runnable {
    private SharedPrinter sp;
    private int max;

    Odd(SharedPrinter sp, int max) {
        this.sp = sp;
        this.max = max;
    }

    @Override
    public void run() {
        for (int i = 1; i <= max; i = i + 2) {
            sp.printOddNum(i);
        }
    }
}
