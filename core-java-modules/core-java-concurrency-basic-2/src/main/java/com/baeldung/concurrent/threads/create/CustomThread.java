package com.baeldung.concurrent.threads.create;

public class CustomThread extends Thread{

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+" started");
    }

    public static void main(String[] args){
        CustomThread t1 = new CustomThread();
        t1.start();
    }
}