package com.baeldung.intefacenamingconventions;

public class RootUser implements User {
    @Override
    public void identify() {
        System.out.println("I'm a root user.");
    }

    @Override
    public void authorize() {
        System.out.println("Passing through without authorization.");
    }
}
