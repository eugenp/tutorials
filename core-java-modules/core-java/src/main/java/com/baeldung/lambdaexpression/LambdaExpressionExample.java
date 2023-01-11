package com.baeldung.lambdaexpression;

public class LambdaExpressionExample{

    public static void main(String[] args){
        Thread t1 = new Thread(()->System.out.println("Thread: "+Thread.currentThread().getName()+" started"));
        t1.start();
    }
}