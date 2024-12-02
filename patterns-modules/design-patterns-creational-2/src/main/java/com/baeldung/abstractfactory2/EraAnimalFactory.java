package com.baeldung.abstractfactory2;


public interface EraAnimalFactory {

    LandAnimal makeLandAnimal();
    SkyAnimal makeSkyAnimal();
}
