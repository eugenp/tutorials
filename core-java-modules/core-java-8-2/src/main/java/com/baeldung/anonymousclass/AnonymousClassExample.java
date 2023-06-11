package com.baeldung.anonymousclass;

public class AnonymousClassExample{

    public static void main(String[] args){
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("Thread: "+Thread.currentThread().getName()+" started");
            }
        });
        t1.start();
    }
}