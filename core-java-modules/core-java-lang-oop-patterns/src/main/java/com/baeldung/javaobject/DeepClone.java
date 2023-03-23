package com.baeldung.javaobject;

public class DeepClone implements Cloneable{

    private String name;
    private Address address;

    public DeepClone(String name, Address address) {
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

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public DeepClone clone() {
        try {
            DeepClone clone = (DeepClone) super.clone();
            clone.setAddress(clone.getAddress().clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}