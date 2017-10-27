package com.baeldung.concurrent.daemon;

public class NewThread extends Thread {

    public void run() {
        for (int i = 0; i < 10; i++)
            System.out.println("New Thread is running...");
    }
}
