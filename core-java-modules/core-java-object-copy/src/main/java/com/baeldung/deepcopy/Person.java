package com.baeldung.deepcopy;

public class Person implements Cloneable {

    private String name;
    private int age;
    private Address address;

    public Person(Person other) {
        this(other.name, other.age, new Address(other.address));
    }

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public Person clone() {
        try {
            Person copy = (Person) super.clone();
            copy.setAddress(this.address.clone());
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
