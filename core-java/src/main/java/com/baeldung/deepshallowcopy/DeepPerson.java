package com.baeldung.deepshallowcopy;

public class DeepPerson implements Cloneable {
    String name;
    String surname;
    DeepAddress address;

    @Override
    public Object clone() throws CloneNotSupportedException {
        DeepPerson person = (DeepPerson) super.clone();
        person.address = (DeepAddress) person.address.clone();
        return person;
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

    public DeepAddress getAddress() {
        return address;
    }

    public void setAddress(DeepAddress address) {
        this.address = address;
    }

    public DeepPerson(String name, String surname, DeepAddress address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
