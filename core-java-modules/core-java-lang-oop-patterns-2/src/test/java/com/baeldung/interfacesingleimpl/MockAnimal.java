package com.baeldung.interfacesingleimpl;

public class MockAnimal implements Animal {
    @Override
    public String makeSound() {
        return "Mock animal sound!";
    }
}
