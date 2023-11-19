package com.baeldung.interfacesingleimpl;

public class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String makeSound() {
        return "Meow! My name is " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
