package org.casting.demo;

public class Warrior extends Character {

    // standard constructor
    public Warrior(String name) {
        super(name);
    }

    public void obeyCommand(String command) {
        System.out.println(String.format("Warrior [%s] obeys a command [%s]: ", this.getName(), command));
    }

}
