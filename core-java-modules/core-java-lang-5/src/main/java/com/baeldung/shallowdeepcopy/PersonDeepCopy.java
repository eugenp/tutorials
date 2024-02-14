package com.baeldung.deepshallowcopy;

public class PersonDeepCopy implements Cloneable {
    private String name;
    private AddressDeepCopy address;

    public PersonDeepCopy(String name, AddressDeepCopy address) {
        this.name = name;
        this.address = address;
    }

    //Setters and Getters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(AddressDeepCopy address) {
        this.address = address;
    }

    public AddressDeepCopy getAddress() {
        return address;
    }

    protected Object clone() throws CloneNotSupportedException {
        PersonDeepCopy person = (PersonDeepCopy) super.clone();
        person.address = (AddressDeepCopy) address.clone();
        return person;
    }
}