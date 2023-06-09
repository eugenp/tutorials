package core;

import core.Address;

/**
 * Model class representing a Person
 */
public class Person {
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Shallow copy implementation
    public Person shallowCopy() {
        return new Person(this.name, this.age, this.address);
    }

    // Deep copy implementation
    public Person deepCopy() {
        Address copiedAddress = new Address(this.address.getCity());
        return new Person(this.name, this.age, copiedAddress);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

