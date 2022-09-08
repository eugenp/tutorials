package com.baeldung.clone.shallow;

public class Person implements Cloneable {

    private String name;
    private int age;
    private Address address;

    public Person() {
        super();
    }

    public Person(String name, int age, Address address) {
        super();
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Person(Person person) {
        super();
        this.name = person.getName();
        this.age = person.getAge();
        this.address = person.getAddress();
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

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + ", address=" + address + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
