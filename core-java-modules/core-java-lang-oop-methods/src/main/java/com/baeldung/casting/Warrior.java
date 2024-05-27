package com.baeldung.casting;

public class Warrior extends Character {

    // standard constructor
    public Warrior(String name) {
        super(name);
    }

    public void obeyCommand(String command) {
        System.out.printf("Warrior [%s] obeys a command [%s]: %n", this.getName(), command);
    }

}
