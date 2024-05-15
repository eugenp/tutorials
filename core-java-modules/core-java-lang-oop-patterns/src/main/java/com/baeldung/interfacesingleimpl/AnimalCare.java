package com.baeldung.interfacesingleimpl;

public class AnimalCare {
    private Animal animal;

    public AnimalCare(Animal animal) {
        this.animal = animal;
    }

    public String animalSound() {
        return animal.makeSound();
    }
}
