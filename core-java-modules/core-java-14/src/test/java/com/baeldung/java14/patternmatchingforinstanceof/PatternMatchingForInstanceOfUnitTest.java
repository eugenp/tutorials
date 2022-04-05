package com.baeldung.java14.patternmatchingforinstanceof;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.baeldung.java14.patternmatchingforinstanceof.PatternMatchingForInstanceOf.Cat;
import com.baeldung.java14.patternmatchingforinstanceof.PatternMatchingForInstanceOf.Dog;

class PatternMatchingForInstanceOfUnitTest {

    @Test
    void givenAnAnimal_whenTypeIsCat_ThenCatGoesMeow() {
        Cat animal = mock(Cat.class);

        PatternMatchingForInstanceOf instanceOf = new PatternMatchingForInstanceOf();
        instanceOf.performAnimalOperations(animal);

        verify(animal).meow();
    }

    @Test
    void givenAnAnimal_whenTypeIsDog_ThenDogGoesWoof() {
        Dog animal = mock(Dog.class);

        PatternMatchingForInstanceOf instanceOf = new PatternMatchingForInstanceOf();
        instanceOf.performAnimalOperations(animal);

        verify(animal).woof();
    }

}
