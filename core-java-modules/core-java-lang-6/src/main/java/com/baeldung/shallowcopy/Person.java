package com.baeldung.shallowcopy;

public class Person implements Cloneable{
    String name;
    Address address;

    public Person(String name, Address address){
        this.name=name;
        this.address=address;
    }

    //implementing default version of clone() method
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public void setAddress( Address address) {
        this.address = address;
    }
}