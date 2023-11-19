package com.baeldung.aws.data;

public class User {

    private String name;
    private String surname;
    private String emailAddress;

    public User(String name, String surname, String emailAddress) {
        this.name = name;
        this.surname = surname;
        this.emailAddress = emailAddress;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
