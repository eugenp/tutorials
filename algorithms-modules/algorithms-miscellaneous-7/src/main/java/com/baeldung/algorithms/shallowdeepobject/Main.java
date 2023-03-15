package com.baeldung.algorithms.shallowdeepobject;

public class Main{
    public static void main(String[] args) throws CloneableNotSupported{

        Address addressOne = new Address("9/11", "Akinola Sholanke Street", "Lagos");
        User userOne = new User("Moh", 171170, addressOne);

        User userTwo = (User) userOne.clone();

        System.out.println("Original Object: " + userOne.toString());
        System.out.println("Cloned Object: " + userTwo.toString());

        userOne.address.streetName = "Musari Apena Street";

        System.out.println("After changing the street name on the original object, we have:");
        System.out.println("Original Object: " + userOne.toString());
        System.out.println("Cloned Object: " + userTwo.toString());
    }
}