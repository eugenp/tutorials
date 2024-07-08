package com.baeldung.upcastingvsdowncasting;

class Dog extends Animal {
    public void makeSound() {
        System.out.println("Bark");
    }

    public void fetch() {
        System.out.println("Dog fetches");
    }
}
