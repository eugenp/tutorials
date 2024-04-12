package com.baeldung.concurrent.threads.create;

public class TestClass implements Runnable{

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+" started");
    }

    public static void main(String[] args){
        TestClass testClassRef = new TestClass();
        Thread t1 = new Thread(testClassRef);
        t1.start();
    }
}