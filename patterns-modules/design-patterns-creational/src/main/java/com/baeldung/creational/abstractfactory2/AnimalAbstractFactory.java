package com.baeldung.creational.abstractfactory2;

public class AnimalAbstractFactory {

    Animal animal;
    Animal createAnimal(AnimalType type) {
        AnimalEra era = getFromConfiguration();
        switch (era) {
        case MESOZOIC:
            animal = new MesozoicAnimalFactory().createAnimal(type);
            break;
        case CENOZOIC:
            animal = new CenozoicAnimalFactory().createAnimal(type);
            break;
        }
        return null;
    }

    AnimalEra getFromConfiguration() {
        return AnimalEra.MESOZOIC;
    }
}
