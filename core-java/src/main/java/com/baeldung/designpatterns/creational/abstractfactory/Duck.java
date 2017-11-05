package com.baeldung.designpatterns.creational.abstractfactory;

public class Duck implements Toy {

    @Override
    public String getType() {
        return "Duck";
    }

    @Override
    public String makeSound() {
        return "Squeks";
    }

}
