package com.baeldung.deepshallowcopy;

class Employee implements Cloneable {

    String name;
    int age;
    Address address;

    Employee(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        // Call clone method of Object class to create a shallow copy of the Employee object
        Employee clonedPerson = (Employee) super.clone();

        // Create a new Address object by cloning the original address
        clonedPerson.address = new Address(this.address.city, this.address.street); // Create a new Address object

        return clonedPerson;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", address=" + address + '}';
    }
}
