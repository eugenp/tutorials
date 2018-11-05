package com.baeldung.concurrent.evenandodd;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {

        SharedPrinter sp = new SharedPrinter();
        Thread odd = new Thread(new Odd(sp, 10));
        odd.setName("Odd");
        Thread even = new Thread(new Even(sp, 10));
        even.setName("Even");

        odd.start();
        even.start();

    }

}

class SharedPrinter {

    Semaphore semEven = new Semaphore(0);
    Semaphore semOdd = new Semaphore(1);

    public void printEvenNum(int num) {
        try {
            semEven.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()
            .getName() + ":"+num);
        semOdd.release();
    }

    public void printOddNum(int num) {
        try {
            semOdd.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()
            .getName() + ":"+ num);
        semEven.release();

    }
}

class Even implements Runnable {
    SharedPrinter sp;
    int max;

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
    SharedPrinter sp;
    int max;

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
