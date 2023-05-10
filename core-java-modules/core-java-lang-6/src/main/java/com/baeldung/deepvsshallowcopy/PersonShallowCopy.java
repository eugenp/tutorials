package com.baeldung.deepvsshallowcopy;

public class PersonShallowCopy implements Cloneable{
    String name;
    AddressShallowCopy address;

    public PersonShallowCopy(String name, AddressShallowCopy address){
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

    public AddressShallowCopy getAddress() {
        return address;
    }

    public void setAddress( AddressShallowCopy address) {
        this.address = address;
    }
}