package org.baeldung.bean.injection;

/**
 * Animal class is inject to the Forest class using setter-based DI.
 */
public class Forest {
    private Animal animal;

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }
}
