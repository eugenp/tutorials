package com.baeldung.intefacenamingconventions;

public class RegularUser implements User {
    @Override
    public void identify() {
        System.out.println("I'm just a regular user.");
    }

    @Override
    public void authorize() {
        System.out.println("Authorizing with credentials.");
    }
}
