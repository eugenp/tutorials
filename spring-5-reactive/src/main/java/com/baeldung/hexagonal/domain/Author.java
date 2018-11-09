package com.baeldung.hexagonal.domain;

public class Author {

    private String fullName;
    private String firstName;
    private String initials;
    private String surname;

    public Author(String fullName, String firstName, String initials, String surname) {
        this.fullName = fullName;
        this.firstName = firstName;
        this.initials = initials;
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Author{" +
                "fullName='" + fullName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", initials='" + initials + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
