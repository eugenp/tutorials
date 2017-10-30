package com.baeldung.creationaldp.abstractfactory;

public class Duck implements Toy {

    @Override
    public String getToy() {
        return "Duck";
    }

    @Override
    public String makeSound() {
        return "Squeks";
    }

}
