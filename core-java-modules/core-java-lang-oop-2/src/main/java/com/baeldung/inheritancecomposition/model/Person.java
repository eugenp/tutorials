package com.baeldung.inheritancecomposition.model;

public class Person {
    
    private final String name;
    private final String email;
    private final int age;
    
    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", email=" + email + ", age=" + age + "}";
    }
}
