package com.baeldung.keyword.superkeyword;

/**
 * Created by Gebruiker on 5/15/2018.
 */
public class SubClass extends SuperKeyword {

    String message = "child class";

    public SubClass(String message) {
        super(message);
    }

    public SubClass() {
        super.printMessage();
        printMessage();
    }

    public void getParentMessage() {
        System.out.println(super.message);
    }

    public void printMessage() {
        System.out.println(message);
    }
}
