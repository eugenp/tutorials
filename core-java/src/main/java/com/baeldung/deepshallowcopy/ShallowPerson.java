package com.baeldung.deepshallowcopy;

public class ShallowPerson implements Cloneable {
    String name;
    String surname;
    ShallowAddress address;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ShallowAddress getAddress() {
        return address;
    }

    public void setAddress(ShallowAddress address) {
        this.address = address;
    }

    public ShallowPerson(String name, String surname, ShallowAddress address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
