package com.baeldung.java14.patternmatchingforinstanceof;

public class PatternMatchingForInstanceOf {

    public void performAnimalOperations(Animal animal) {
         if (animal instanceof Cat cat) {
             cat.meow();
         } else if(animal instanceof Dog dog) {
             dog.woof();
         }
    }

    private abstract class Animal {
    }

    private final class Cat extends Animal {
        private void meow() {
        }
    }

    private final class Dog extends Animal {
        private void woof() {
        }
    }

}
