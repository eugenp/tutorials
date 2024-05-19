package org.casting.demo;

public class Commander extends Character {

    // standard constructor
    public Commander(String name) {
        super(name);
    }

    public void issueCommand(String command) {
        System.out.println(String.format("Commander [%s] issues a command [%s]: ", this.getName(), command));
    }
}

