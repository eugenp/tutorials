package com.baeldung.deepshallowcopy;

class ShallowCopy {

    public static void main(String[] args) {
        try {
            // Create an Address object
            Address address = new Address("New York", "5th Avenue");

            // Create an original Person object with the Address object
            Person original = new Person("John", 30, address);

            // Clone the original Person object to create a shallow copy
            Person copy = (Person) original.clone();

            // Print the original and copied Person objects
            System.out.println("Original: " + original);
            System.out.println("Copy: " + copy);

            // Modify the fields of the original Person object
            original.name = "Doe";
            original.age = 25;
            original.address.city = "Los Angeles"; // Modify the mutable Address object

            // Print the original and copied Person objects after modification
            System.out.println("After modifying the original:");
            System.out.println("Original: " + original);
            System.out.println("Copy: " + copy); // Observe the changes in the mutable Address object

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}