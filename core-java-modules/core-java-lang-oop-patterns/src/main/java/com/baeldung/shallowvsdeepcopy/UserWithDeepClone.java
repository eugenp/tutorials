package com.baeldung.shallowvsdeepcopy;

public class UserWithDeepClone implements Cloneable{

    private String name;
    private Address address;
    private int age;

    public UserWithDeepClone(String name, Address address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public UserWithDeepClone clone() {
        try {
            UserWithDeepClone clone = (UserWithDeepClone) super.clone();
            clone.setAddress(clone.getAddress().clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}