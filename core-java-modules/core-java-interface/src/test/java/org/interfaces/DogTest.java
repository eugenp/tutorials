package org.interfaces;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DogTest {

    @Test
    public void givenDogIsAAnimal_whenEatMethodIsCalled_ThenAssertIfDogIsEating() {
        Dog dog = new Dog();

        String response = dog.eat();

        assertEquals("Dog is eating", response);
    }

    @Test
    public void givenDogIsAAnimal_whenSleepMethodIsCalled_ThenAssertIfDogIsSleeping() {
        Dog dog = new Dog();

        String response = dog.sleep();

        assertEquals("Dog is sleeping", response);
    }
}