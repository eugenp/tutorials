package com.java.baeldung.transientkw;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -2936687026040726549L;

    private String firstName;
    private transient String middleName;
    private String lastName;
    private transient int age;
    private final transient String major = "CS";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }
}
