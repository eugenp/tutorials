package org.interfaces;

public class Dog implements Animal {
    @Override
    public String eat() {
        return "Dog is eating";
    }

    @Override
    public String sleep() {
        return "Dog is sleeping";
    }
}
