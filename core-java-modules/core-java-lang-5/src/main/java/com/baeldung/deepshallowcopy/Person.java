package com.baeldung.deepshallowcopy;

import java.io.*;

public class Person implements Serializable,Cloneable {

    private Long userId;
    private String username;
    private int age;
    private Address address;

    public Person(Long userId, String username, int age, Address address) {
        this.userId = userId;
        this.username = username;
        this.age = age;
        this.address = address;
    }

    public Person deepCopy() throws IOException, ClassNotFoundException {
        // serialize the object
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);

        // deserialize the object
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Person) ois.readObject();
    }

    // getters and setters
    @Override
    protected Object clone() throws CloneNotSupportedException {

        Person person = (Person) super.clone();

        // use this when deep Copy using Cloneable
        // person.address = (Address) address.clone();
        return person;
    }

    // getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }



}
