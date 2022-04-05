package com.baeldung.casting;

import java.util.List;

public class AnimalFeeder {

    public void feed(List<Animal> animals) {
        animals.forEach(animal -> {
            animal.eat();
            if (animal instanceof Cat) {
                ((Cat) animal).meow();
            }
        });
    }

    public void uncheckedFeed(List<Animal> animals) {
        animals.forEach(animal -> {
            animal.eat();
            ((Cat) animal).meow();
        });
    }

}
