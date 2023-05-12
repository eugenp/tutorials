package com.baeldung.deepcopy;


public class Person implements Cloneable{
    String name;
    Address address;
    public Person(String name, Address address){
        this.name=name;
        this.address=address;
    }

    //overriding the clone() method by cloning each individual object
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Person person = (Person) super.clone();
        person.address = (Address) address.clone();
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}