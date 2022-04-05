package com.baeldung.lazyinitialization.services;

public class Writer {

    private final String writerId;

    public Writer(String writerId) {
        this.writerId = writerId;
        System.out.println(writerId + " initialized!!!");
    }

    public void write(String message) {
        System.out.println(writerId + ": " + message);
    }

}
