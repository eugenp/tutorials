package com.baeldung.thisescape;

public class LoggerRunnable implements Runnable {

    public LoggerRunnable() {
        Thread thread = new Thread(this); // this escapes
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Started...");
    }
}
