package com.baeldung.deepshallowcopy;

class Person implements Cloneable {

    String name;
    int age;
    Address address;

    // Constructor to initialize Person object
    Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Create a shallow copy of the Person object
        return super.clone();
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", address=" + address + '}';
    }

}