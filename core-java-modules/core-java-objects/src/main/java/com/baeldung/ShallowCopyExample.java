package com.baeldung;

public class ShallowCopyExample {
    public static void main(String[] args) {
        Address address = new Address("Glasgow");
        Person person1 = new Person("John", address);
        Person person2 = person1.shallowCopy();

        // Modifying the city of person2's address
        person2.address.city = "London";

        // Modifying person2's name
        person2.name = "Scott";

        // Both person1 and person2 share the same address object
        System.out.println(person1.address.city);  // Output: London
        System.out.println(person1.name);  // Output: John
        System.out.println(person2.name);  // Output: Scott
    }
}

