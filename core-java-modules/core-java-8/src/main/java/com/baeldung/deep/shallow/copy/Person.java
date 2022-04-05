package com.baeldung.deep.shallow.copy;

public class Person implements Cloneable {
    private String name;
    private int age;
    private Contact contactDetail;

    public Person(String name, int age, Contact contactDetail) {
        this.name = name;
        this.age = age;
        this.contactDetail = contactDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Contact getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(Contact contactDetail) {
        this.contactDetail = contactDetail;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person = (Person) super.clone();
        person.setContactDetail((Contact) contactDetail.clone());
        return person;
    }
}