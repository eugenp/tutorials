package com.baeldung;

public class DeepCopyExample {
    public static void main(String[] args) {
        Address address = new Address("Glasgow");
        Person person1 = new Person("John", address);
        Person person2 = person1.deepCopy();

        // Modifying the city of person2's address
        person2.address.city = "London";

        // Both person1 and person2 share the same address object
        System.out.println(person1.address.city);  // Output: Glasgow
        System.out.println(person2.address.city);  // Output: London
    }
}
