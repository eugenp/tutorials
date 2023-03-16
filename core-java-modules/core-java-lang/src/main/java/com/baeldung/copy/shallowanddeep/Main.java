package com.baeldung.copy.shallowanddeep;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        // shallow copy
        Person patrick = new Person("Patrick", 24);
        Person patrickCopy = (Person) patrick.clone();

        System.out.println(patrick.name + " " + patrick.age);
        System.out.println(patrickCopy.name + " " + patrickCopy.age);

        patrickCopy.age = 26;
        System.out.println(patrick.name + " " + patrick.age);
        System.out.println(patrickCopy.name + " " + patrickCopy.age);

        // deep copy
        Address address = new Address("123 Main St", "Kampala");
        Employee jesca = new Employee("Janet Jesca", 29, address);

        Employee jescaCopy = (Employee) jesca.clone();

        jescaCopy.getAddress()
            .setStreet("Plot 25, Munyonyo");

        System.out.println(jesca.getAddress()
            .getStreet());
        System.out.println(jescaCopy.getAddress()
            .getStreet());

    }
}
