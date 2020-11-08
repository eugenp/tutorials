package com.baeldung.casting;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CastingUnitTest {

    @Test
    public void whenPrimitiveConverted_thenValueChanged() {
        double myDouble = 1.1;
        int myInt = (int) myDouble;

        assertNotEquals(myDouble, myInt);
    }

    @Test
    public void whenUpcast_thenInstanceUnchanged() {
        Cat cat = new Cat();
        Animal animal = cat;
        animal = (Animal) cat;
        assertTrue(animal instanceof Cat);
    }

    @Test
    public void whenUpcastToObject_thenInstanceUnchanged() {
        Object object = new Animal();
        assertTrue(object instanceof Animal);
    }

    @Test
    public void whenUpcastToInterface_thenInstanceUnchanged() {
        Mew mew = new Cat();
        assertTrue(mew instanceof Cat);
    }

    @Test
    public void whenUpcastToAnimal_thenOverridenMethodsCalled() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());
        new AnimalFeeder().feed(animals);
    }

    @Test
    public void whenDowncastToCat_thenMeowIsCalled() {
        Animal animal = new Cat();
        ((Cat) animal).meow();
    }

    @Test(expected = ClassCastException.class)
    public void whenDownCastWithoutCheck_thenExceptionThrown() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());
        new AnimalFeeder().uncheckedFeed(animals);
    }

    @Test
    public void whenDowncastToCatWithCastMethod_thenMeowIsCalled() {
        Animal animal = new Cat();
        if (Cat.class.isInstance(animal)) {
            Cat cat = Cat.class.cast(animal);
            cat.meow();
        }
    }
    
    @Test
    public void whenParameterCat_thenOnlyCatsFed() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat());
        animals.add(new Dog());
        AnimalFeederGeneric<Cat> catFeeder = new AnimalFeederGeneric<Cat>(Cat.class);
        List<Cat> fedAnimals = catFeeder.feed(animals);

        assertTrue(fedAnimals.size() == 1);
        assertTrue(fedAnimals.get(0) instanceof Cat);
    }
}
