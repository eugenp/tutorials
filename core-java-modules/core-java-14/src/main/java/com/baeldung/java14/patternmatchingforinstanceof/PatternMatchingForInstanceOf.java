package com.baeldung.java14.patternmatchingforinstanceof;

public class PatternMatchingForInstanceOf {

    public void performAnimalOperations(Animal animal) {
         if (animal instanceof Cat cat) {
             cat.meow();
         } else if(animal instanceof Dog dog) {
             dog.woof();
         }
    }

    abstract class Animal {
    }

    final class Cat extends Animal {
        void meow() {
        }
    }

    final class Dog extends Animal {
        void woof() {
        }
    }

}
