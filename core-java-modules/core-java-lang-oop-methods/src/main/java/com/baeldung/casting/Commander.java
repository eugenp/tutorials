package com.baeldung.casting;

public class Commander extends Character {

    // standard constructor
    public Commander(String name) {
        super(name);
    }

    public void issueCommand(String command) {
        System.out.printf("Commander [%s] issues a command [%s]: %n", this.getName(), command);
    }
}

