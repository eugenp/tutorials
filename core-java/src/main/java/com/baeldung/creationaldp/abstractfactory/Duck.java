package com.baeldung.creationaldp.abstractfactory;

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
