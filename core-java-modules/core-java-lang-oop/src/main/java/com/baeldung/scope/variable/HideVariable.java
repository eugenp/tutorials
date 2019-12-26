package com.baeldung.scope.variable;

/**
 * Created by Gebruiker on 5/6/2018.
 */
public class HideVariable {

    private String message = "this is instance variable";

    HideVariable() {
        String message = "constructor local variable";
        System.out.println(message);
    }

    public void printLocalVariable() {
        String message = "method local variable";
        System.out.println(message);
    }

    public void printInstanceVariable() {
        System.out.println(this.message);
    }
}
