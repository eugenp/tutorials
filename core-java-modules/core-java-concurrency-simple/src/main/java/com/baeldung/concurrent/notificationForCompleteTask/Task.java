package com.baeldung.concurrent.notificationForCompleteTask;

public class Task implements Runnable{
    @Override
    public void run() {
        System.out.println("Task in progress");
        // Business logic goes here
    }
}
