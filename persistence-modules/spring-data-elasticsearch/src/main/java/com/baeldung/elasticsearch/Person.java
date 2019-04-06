package com.baeldung.elasticsearch;

import java.util.Date;

public class Person {

    private int age;

    private String fullName;

    private Date dateOfBirth;

    public Person() {
    }

    Person(int age, String fullName, Date dateOfBirth) {
        super();
        this.age = age;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person [age=" + age + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + "]";
    }
}
