package com.baeldung.upcastingvsdowncasting;

public class Main {
    public static void main(String[] args) {
        // Upcasting
        Animal myDog = new Dog();
        myDog.makeSound();
        // Upcasting
        Animal myAnimal = new Dog();
        Dog myDog2 = (Dog) myAnimal;
        myDog2.makeSound();
        myDog2.fetch();
    }
}
