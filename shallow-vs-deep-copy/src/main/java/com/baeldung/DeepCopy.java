package com.baeldung;

public class DeepCopy {

    public static void main(String[] args) {
        Dog dog = new Dog("Rex", 5);
        // deep copy of dog via copy constructor
        Dog dogDeepCopyConstructor = new Dog(dog);
        System.out.println(dog);
        System.out.println(dogDeepCopyConstructor);
        // deep copy of dog via clone method
        Dog deepCopyClone = dog.clone();
        System.out.println(dog);
        System.out.println(deepCopyClone);
    }
}