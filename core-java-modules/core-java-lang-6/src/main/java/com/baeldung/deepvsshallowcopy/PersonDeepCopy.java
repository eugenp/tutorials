package com.baeldung.deepvsshallowcopy;


public class PersonDeepCopy implements Cloneable{
    String name;
    AddressDeepCopy address;
    public PersonDeepCopy(String name, AddressDeepCopy address){
        this.name=name;
        this.address=address;
    }

    //overriding the clone() method by cloning each individual object
    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        PersonDeepCopy person = (PersonDeepCopy) super.clone();
        person.address = (AddressDeepCopy) address.clone();
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDeepCopy getAddress() {
        return address;
    }

    public void setAddress(AddressDeepCopy address) {
        this.address = address;
    }
}