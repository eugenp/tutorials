package com.baeldung.deepshallowcopy;

class DeepCopy {

    public static void main(String[] args) {
        try {
            Address address = new Address("New York", "5th Avenue");
            Employee original = new Employee("John", 30, address);
            Employee copy = (Employee) original.clone();

            System.out.println("Original: " + original);
            System.out.println("Copy: " + copy);

            original.name = "Doe";
            original.age = 25;
            original.address.city = "Los Angeles"; // Modify the original Address object

            System.out.println("After modifying the original:");
            System.out.println("Original: " + original);
            System.out.println("Copy: " + copy); // No change in the copied Address object

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
