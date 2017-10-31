package com.baeldung.creationaldp.abstractfactory;

public class Dog implements Toy {

    @Override
    public String getType() {
        return "Dog";
    }

    @Override
    public String makeSound() {
        return "Barks";
    }

}
