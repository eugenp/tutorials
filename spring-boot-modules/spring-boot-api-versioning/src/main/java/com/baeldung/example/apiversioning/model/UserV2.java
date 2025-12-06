package com.baeldung.example.apiversioning.model;

public class UserV2 {
    private String name;
    private String email;
    private int age;

    public UserV2(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
}