package com.shaheen.hexa.core.domain;


public class Person {
    Long id;
    String name;
    String firstName;
    Integer age;

    public Person(Long id, String name, String firstName, Integer age) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAge() {
        return age;
    }
}
