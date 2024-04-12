package com.baledung.modifystream.entity;

public class ImmutablePerson {

    private String name;
    private String email;

    public ImmutablePerson(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public ImmutablePerson withEmail(String email) {

        return new ImmutablePerson(this.name, email);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
