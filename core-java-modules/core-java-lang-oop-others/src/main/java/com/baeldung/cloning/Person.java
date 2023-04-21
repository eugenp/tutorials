package com.baeldung.cloning;

import java.io.*;

public class Person implements Cloneable, Serializable {

    private String firstName;
    private String lastName;
    private Address address;

    public Person(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(Person person) {
        this(person.getFirstName(), person.getLastName(), new Address(person.getAddress()));
    }

    @Override
    public Object clone() {
        Person person;
        try {
            person = (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            person = new Person(this.getFirstName(), this.getLastName(), this.getAddress());
        }
        person.address = (Address) this.address.clone();
        return person;
    }

    public Person deepCopy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        return (Person) objectInputStream.readObject();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
