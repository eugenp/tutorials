package com.baeldung;

public class ShallowCopy {

    public static void main(String[] args) {
        Dog dog = new Dog("Rex", 5);
        Dog dogShallowCopy = dog;

        System.out.println(dog);
        System.out.println(dogShallowCopy);
    }
}