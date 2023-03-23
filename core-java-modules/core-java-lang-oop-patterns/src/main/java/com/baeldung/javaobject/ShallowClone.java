package com.baeldung.javaobject;

public class ShallowClone implements Cloneable{

    private String name;
    private Address address;
   

    public ShallowClone(String name, Address address) {
        this.name = name;
        this.address = address;
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

    // public void setAddress(Address address) {
    //     this.address = address;
    // }


    @Override
    public ShallowClone clone() {
        try {
            ShallowClone clone = (ShallowClone) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}