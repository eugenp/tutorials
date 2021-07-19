package com.baeldung.thisescape;

public class SafePublication implements Runnable {

    private final Thread thread;

    public SafePublication() {
        thread = new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("Started...");
    }

    public void start() {
        thread.start();
    }

    public static void main(String[] args) {
        SafePublication publication = new SafePublication();
        publication.start();
    }
}
