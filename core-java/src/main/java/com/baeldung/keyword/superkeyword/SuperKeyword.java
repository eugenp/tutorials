package com.baeldung.keyword.superkeyword;

/**
 * Created by Gebruiker on 5/14/2018.
 */
public class SuperKeyword {

    String message = "super class";

    public SuperKeyword() {
    }

    public SuperKeyword(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(message);
    }
}
