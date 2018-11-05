package com.baeldung.concurrent.evenandodd;

public class PrintEvenOdd {

    public static void main(String... args) {
        Printer print = new Printer();
        Thread t1 = new Thread(new TaskEvenOdd(print, 10, false));
        t1.setName("Odd");
        Thread t2 = new Thread(new TaskEvenOdd(print, 10, true));
        t2.setName("Even");
        t1.start();
        t2.start();
    }
}

class TaskEvenOdd implements Runnable {
    private int max;
    private Printer print;
    private boolean isEvenNumber;

    TaskEvenOdd(Printer print, int max, boolean isEvenNumber) {
        this.print = print;
        this.max = max;
        this.isEvenNumber = isEvenNumber;
    }

    @Override
    public void run() {
        int number = isEvenNumber == true ? 2 : 1;
        while (number <= max) {
            if (isEvenNumber) {
                print.printEven(number);
            } else {
                print.printOdd(number);
            }
            number += 2;
        }
    }
}

class Printer {
    boolean isOdd = false;

    synchronized void printEven(int number) {
        while (isOdd == false) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread()
            .getName() + ":" + number);
        isOdd = false;
        notify();
    }

    synchronized void printOdd(int number) {
        while (isOdd == true) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread()
            .getName() + ":" + number);
        isOdd = true;
        notify();
    }

}
