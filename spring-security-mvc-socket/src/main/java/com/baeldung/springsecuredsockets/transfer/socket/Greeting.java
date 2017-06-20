package com.baeldung.springsecuredsockets.transfer.socket;

public class Greeting {
    private int id;
    private String message;

    public Greeting() {
    }

    public Greeting(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}