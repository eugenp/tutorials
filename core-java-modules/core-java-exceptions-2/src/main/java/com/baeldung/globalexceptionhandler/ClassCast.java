package com.baeldung.globalexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Animal {

}

class Dog extends Animal {

}

class Lion extends Animal {

}

public class ClassCast {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassCast.class);

    public static void main(String[] args) {

        try {
            Animal animalOne = new Dog(); // At runtime the instance is dog
            Dog bruno = (Dog) animalOne; // Downcasting

            Animal animalTwo = new Lion(); // At runtime the instance is animal
            Dog tommy = (Dog) animalTwo; // Downcasting
        } catch (ClassCastException e) {
            LOGGER.error("ClassCastException caught!");
        }

    }

}
