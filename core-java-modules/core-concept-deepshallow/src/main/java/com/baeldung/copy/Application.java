package com.baeldung.copy;

public class Application {

    public static class Main {
        public static void main(String[] args) throws CloneNotSupportedException {
            // Create an Address
            Address address = new Address("New York");

            // Create a Person
            Person person1 = new Person("John", address);

            // Perform shallow copy
            Person person2 = (Person) person1.clone();

            // Modify the address in person2
            person2.address.city = "San Francisco";

            // Print details to demonstrate changes
            System.out.println("Original Person: " + person1.name + ", " + person1.address.city);
            System.out.println("Cloned Person: " + person2.name + ", " + person2.address.city);

            // Perform deep copy
            Person person3 = person1.deepClone();

            // Modify the address in person3
            person3.address.city = "Los Angeles";

            // Print details to demonstrate changes
            System.out.println("Original Person: " + person1.name + ", " + person1.address.city);
            System.out.println("Deep Cloned Person: " + person3.name + ", " + person3.address.city);
        }
    }
}
