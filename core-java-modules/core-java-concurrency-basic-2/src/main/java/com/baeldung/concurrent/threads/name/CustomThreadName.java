package com.baeldung.concurrent.threads.name;

public class CustomThreadName {

    public int currentNumber = 1;

    public int N = 5;

    public static void main(String[] args) {

        CustomThreadName test = new CustomThreadName();

        Thread oddThread = new Thread(() -> {
            test.printOddNumber();
            // Uncomment below to set thread name using setName() Method
            // Thread.currentThread().setName("ODD");
        }, "ODD");
        // or Uncomment below to set thread name using setName() Method
        // oddThread.setName("ODD");

        Thread evenThread = new Thread(() -> {
            test.printEvenNumber();
            // Uncomment below to set thread name using setName() Method
            // Thread.currentThread().setName("EVEN");
        }, "EVEN");

        // evenThread.setName("EVEN");

        evenThread.start();
        oddThread.start();

    }

    public void printEvenNumber() {
        synchronized (this) {
            while (currentNumber < N) {
                while (currentNumber % 2 == 1) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread()
                    .getName() + " --> " + currentNumber);
                currentNumber++;
                notify();
            }
        }
    }

    public void printOddNumber() {
        synchronized (this) {
            while (currentNumber < N) {
                while (currentNumber % 2 == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread()
                    .getName() + " --> " + currentNumber);
                currentNumber++;
                notify();
            }
        }
    }

}
