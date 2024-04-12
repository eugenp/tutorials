package com.baeldung.superkeyword;

/**
 * Created by Gebruiker on 5/14/2018.
 */
public class SuperBase {

    String message = "super class";

    public SuperBase() {
    }

    public SuperBase(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }
}
