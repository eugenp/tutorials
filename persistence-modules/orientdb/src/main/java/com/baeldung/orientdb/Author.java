package com.baeldung.orientdb;

import javax.persistence.Id;

public class Author  {
    @Id
    private Object id;

    private String firstName;
    private String lastName;
    private int level;

    public Author() {
    }

    public Author(String firstName, String lastName, int level) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level=" + level +
                '}';
    }
}
