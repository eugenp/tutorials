package com.baeldung.junit5.nested;

public class User {
    private String name;
    private Membership membership;

    public User(String name, Membership membership) {
        this.name = name;
        this.membership = membership;
    }

    public String getName() {
        return name;
    }

    public Membership getMembership() {
        return membership;
    }
}