package com.baeldung.creational.abstractfactory2;

public class SkyAnimal extends Animal{

    SkyAnimal(AnimalEra era, String name) {
        super(AnimalType.SKY, era, name);
        create();
    }
    @Override
    void create() {
        System.out.println("Creating a " + type + " animal: " + name);
    }
}
