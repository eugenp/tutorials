package com.baeldung.creational.abstractfactory2;

public class CenozoicAnimalFactory implements EraAnimalFactory{
    @Override
    public LandAnimal makeLandAnimal() {
        return new LandAnimal(AnimalEra.CENOZOIC, "Mammoth");
    }

    @Override
    public SkyAnimal makeSkyAnimal() {
        return new SkyAnimal(AnimalEra.CENOZOIC, "Terror bird");
    }

    Animal createAnimal(AnimalType type) {
        switch (type) {
        case LAND:
            return makeLandAnimal();
        case SKY:
            return makeSkyAnimal();
        }
        return null;
    }
}
