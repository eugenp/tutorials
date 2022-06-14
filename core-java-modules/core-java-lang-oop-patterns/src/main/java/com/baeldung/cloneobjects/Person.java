package com.baeldung.cloneobjects;

public class Person implements Cloneable {

    private String name;
    private int age;
    private Address address;
    private Contact contact;

    public Person(String name, int age, Address address, Contact contact) {
        super();
        this.name = name;
        this.age = age;
        this.address = address;
        this.contact = contact;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        person.contact = (Contact) contact.clone();
        return person;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
